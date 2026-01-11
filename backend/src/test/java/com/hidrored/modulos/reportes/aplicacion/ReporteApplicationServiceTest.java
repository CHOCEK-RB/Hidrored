package com.hidrored.modulos.reportes.aplicacion;

import com.hidrored.modulos.reportes.dominio.IReporteRepository;
import com.hidrored.modulos.reportes.dominio.ImagenAdjunta;
import com.hidrored.modulos.reportes.dominio.modelo.Reporte;
import com.hidrored.modulos.reportes.dominio.modelo.EstadoReporte;
import com.hidrored.modulos.reportes.dominio.modelo.TipoReporte;
import com.hidrored.modulos.reportes.dominio.modelo.PrioridadReporte;
import com.hidrored.modulos.reportes.dominio.Ubicacion;
import com.hidrored.modulos.storage.dominio.IStorageService;
import com.hidrored.modulos.usuarios.dominio.IUsuarioRepository;
import com.hidrored.modulos.usuarios.dominio.Usuario;
import com.hidrored.modulos.reportes.infraestructura.web.ReporteDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReporteApplicationServiceTest {

  @Mock
  private IReporteRepository reporteRepository;
  @Mock
  private IUsuarioRepository usuarioRepository;
  @Mock
  private IStorageService storageService;
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private ReporteApplicationService reporteApplicationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void crearReporte_DeberiaCrearReporteSinImagen_CuandoNoSeProveeImagen() {
    // Given
    String usuarioId = "testUser";
    Usuario mockUsuario = new Usuario(usuarioId, "username", "password", "ROLE_USER");
    CrearReporteCommand command = new CrearReporteCommand(
        usuarioId, "Titulo", "Descripcion", -12.0, -77.0, "Direccion", "FUGA", "ALTA");
    MultipartFile imagenFile = null;

    Reporte mockReporte = new Reporte(usuarioId, "Titulo", "Descripcion",
        new Ubicacion(-12.0, -77.0, "Direccion"), TipoReporte.FUGA, PrioridadReporte.ALTA);
    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(mockUsuario));
    when(reporteRepository.save(any(Reporte.class))).thenReturn(mockReporte);

    // Mock RestTemplate response for geocoding
    ObjectMapper mapper = new ObjectMapper();
    JsonNode mockAddress = mapper.createObjectNode()
        .put("state", "Lima")
        .put("city_district", "Cercado de Lima");
    JsonNode mockResponse = mapper.createObjectNode()
        .set("address", mockAddress);
    when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(mockResponse);

    // When
    ReporteDTO result = reporteApplicationService.crearReporte(command, imagenFile);

    // Then
    assertNotNull(result);
    assertEquals(command.getTitulo(), result.getTitulo());
    assertEquals(EstadoReporte.PENDIENTE.name(), result.getEstado());
    assertNull(result.getImagenAdjunta()); // No imagen adjunta
    verify(reporteRepository, times(1)).save(any(Reporte.class));
    verify(storageService, never()).guardar(any(MultipartFile.class)); // No debería llamar al storageService
  }

  @Test
  void crearReporte_DeberiaCrearReporteConImagen_CuandoSeProveeImagen() {
    // Given
    String usuarioId = "testUser";
    Usuario mockUsuario = new Usuario(usuarioId, "username", "password", "ROLE_USER");
    CrearReporteCommand command = new CrearReporteCommand(
        usuarioId, "Titulo", "Descripcion", -12.0, -77.0, "Direccion", "FUGA", "ALTA");
    MockMultipartFile imagenFile = new MockMultipartFile(
        "imagen", "imagen.jpg", "image/jpeg", "some image data".getBytes());
    String imageUrl = "http://example.com/imagen.jpg";

    Reporte mockReporte = new Reporte(usuarioId, "Titulo", "Descripcion",
        new Ubicacion(-12.0, -77.0, "Direccion"), TipoReporte.FUGA, PrioridadReporte.ALTA);
    mockReporte.setImagenAdjunta(new ImagenAdjunta(imageUrl, imagenFile.getOriginalFilename(),
        imagenFile.getContentType(), imagenFile.getSize(), LocalDateTime.now()));

    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(mockUsuario));
    when(storageService.guardar(imagenFile)).thenReturn(imageUrl);
    when(reporteRepository.save(any(Reporte.class))).thenReturn(mockReporte);

    // Mock RestTemplate response for geocoding
    ObjectMapper mapper = new ObjectMapper();
    JsonNode mockAddress = mapper.createObjectNode()
        .put("state", "Lima")
        .put("city_district", "Cercado de Lima");
    JsonNode mockResponse = mapper.createObjectNode()
        .set("address", mockAddress);
    when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(mockResponse);

    // When
    ReporteDTO result = reporteApplicationService.crearReporte(command, imagenFile);

    // Then
    assertNotNull(result);
    assertEquals(command.getTitulo(), result.getTitulo());
    assertEquals(EstadoReporte.PENDIENTE.name(), result.getEstado());
    assertNotNull(result.getImagenAdjunta());
    assertEquals(imageUrl, result.getImagenAdjunta().getUrl());
    verify(storageService, times(1)).guardar(imagenFile); // Debería llamar al storageService
    verify(reporteRepository, times(1)).save(any(Reporte.class));
  }

  @Test
  void crearReporte_DeberiaLanzarExcepcion_CuandoUsuarioNoExiste() {
    // Given
    String usuarioId = "nonExistentUser";
    CrearReporteCommand command = new CrearReporteCommand(
        usuarioId, "Titulo", "Descripcion", -12.0, -77.0, "Direccion", "FUGA", "ALTA");
    MultipartFile imagenFile = null;

    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

    // When & Then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      reporteApplicationService.crearReporte(command, imagenFile);
    });

    assertEquals("Usuario no encontrado con ID: " + usuarioId, exception.getMessage());
    verify(reporteRepository, never()).save(any(Reporte.class));
    verify(storageService, never()).guardar(any(MultipartFile.class));
  }
}
