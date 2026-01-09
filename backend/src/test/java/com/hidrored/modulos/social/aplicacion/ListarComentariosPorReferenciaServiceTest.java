package com.hidrored.modulos.social.aplicacion;

import com.hidrored.modulos.social.dominio.Comentario;
import com.hidrored.modulos.social.dominio.IComentarioRepository;
import com.hidrored.modulos.social.infraestructura.web.ComentarioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarComentariosPorReferenciaServiceTest {

  @Mock
  private IComentarioRepository comentarioRepository;

  @InjectMocks
  private ListarComentariosPorReferenciaService listarComentariosService;

  @Test
  void listarComentarios_ConReferenciaIdValida_DebeRetornarListaDeComentarios() {
    // Arrange
    String referenciaId = "ref-123";
    Comentario comentario1 = new Comentario("c1", referenciaId, "u1", "Hola", LocalDateTime.now());
    Comentario comentario2 = new Comentario("c2", referenciaId, "u2", "Mundo", LocalDateTime.now().minusMinutes(5));
    when(comentarioRepository.findByReferenciaIdOrderByFechaCreacionDesc(referenciaId))
        .thenReturn(List.of(comentario1, comentario2));

    // Act
    List<ComentarioResponse> respuestas = listarComentariosService.listarComentarios(referenciaId);

    // Assert
    assertNotNull(respuestas);
    assertEquals(2, respuestas.size());
    assertEquals("c1", respuestas.get(0).getId());
    assertEquals("Hola", respuestas.get(0).getContenido());
    assertEquals("c2", respuestas.get(1).getId());
    assertEquals("Mundo", respuestas.get(1).getContenido());
  }

  @Test
  void listarComentarios_SinResultados_DebeRetornarListaVacia() {
    // Arrange
    String referenciaId = "ref-sin-comentarios";
    when(comentarioRepository.findByReferenciaIdOrderByFechaCreacionDesc(referenciaId)).thenReturn(List.of());

    // Act
    List<ComentarioResponse> respuestas = listarComentariosService.listarComentarios(referenciaId);

    // Assert
    assertNotNull(respuestas);
    assertEquals(0, respuestas.size());
  }
}
