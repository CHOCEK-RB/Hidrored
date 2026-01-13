package com.hidrored.modulos.storage.aplicacion;

import com.hidrored.modulos.reportes.dominio.ImagenAdjunta;
import com.hidrored.modulos.storage.dominio.IStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionarArchivoServiceTest {

  @Mock
  private IStorageService storageService;

  @InjectMocks
  private GestionarArchivoService gestionarArchivoService;

  @Test
  void subirArchivo_DebeConstruirImagenAdjuntaCorrectamente() {
    String nombreOriginal = "foto.jpg";
    String contentType = "image/jpeg";
    byte[] contenido = "contenido-falso".getBytes();
    MockMultipartFile file = new MockMultipartFile("file", nombreOriginal, contentType, contenido);
    String urlSimulada = "uuid-generado.jpg";

    when(storageService.guardar(file)).thenReturn(urlSimulada);

    ImagenAdjunta resultado = gestionarArchivoService.subirArchivo(file);

    assertNotNull(resultado);
    assertEquals(urlSimulada, resultado.getUrl());
    assertEquals(nombreOriginal, resultado.getNombreArchivo());
    assertEquals(contentType, resultado.getTipoMime());
    assertEquals((long) contenido.length, resultado.getTamanioBytes());
    assertNotNull(resultado.getFechaSubida());

    verify(storageService, times(1)).guardar(file);
  }
}
