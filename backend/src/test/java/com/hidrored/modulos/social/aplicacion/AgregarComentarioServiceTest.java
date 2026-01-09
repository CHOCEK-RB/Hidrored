package com.hidrored.modulos.social.aplicacion;

import com.hidrored.modulos.social.dominio.Comentario;
import com.hidrored.modulos.social.dominio.IComentarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgregarComentarioServiceTest {

  @Mock
  private IComentarioRepository comentarioRepository;

  @InjectMocks
  private AgregarComentarioService agregarComentarioService;

  private AgregarComentarioCommand command;

  @BeforeEach
  void setUp() {
    command = new AgregarComentarioCommand("ref-123", "REPORTE", "user-abc", "Este es un comentario de prueba.");
  }

  @Test
  void agregarComentario_ConDatosValidos_DebeGuardarComentario() {
    // Arrange
    when(comentarioRepository.save(any(Comentario.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Comentario comentarioGuardado = agregarComentarioService.agregarComentario(command);

    // Assert
    assertNotNull(comentarioGuardado);
    assertEquals("ref-123", comentarioGuardado.getReferenciaId());
    assertEquals("user-abc", comentarioGuardado.getUsuarioId());
    assertEquals("Este es un comentario de prueba.", comentarioGuardado.getContenido());
    assertNotNull(comentarioGuardado.getId());
    assertNotNull(comentarioGuardado.getFechaCreacion());
    verify(comentarioRepository).save(any(Comentario.class));
  }

  @Test
  void agregarComentario_ConContenidoNulo_DebeLanzarExcepcion() {
    // Arrange
    AgregarComentarioCommand comandoInvalido = new AgregarComentarioCommand("ref-123", "REPORTE", "user-abc", null);

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      agregarComentarioService.agregarComentario(comandoInvalido);
    });

    assertEquals("El contenido del comentario no puede estar vacío.", exception.getMessage());
  }

  @Test
  void agregarComentario_ConContenidoVacio_DebeLanzarExcepcion() {
    // Arrange
    AgregarComentarioCommand comandoInvalido = new AgregarComentarioCommand("ref-123", "REPORTE", "user-abc", "  ");

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      agregarComentarioService.agregarComentario(comandoInvalido);
    });

    assertEquals("El contenido del comentario no puede estar vacío.", exception.getMessage());
  }
}
