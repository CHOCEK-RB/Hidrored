package com.hidrored.modulos.social.infraestructura.web;

import tools.jackson.databind.ObjectMapper;
import com.hidrored.modulos.social.aplicacion.AgregarComentarioService;
import com.hidrored.modulos.social.aplicacion.ListarComentariosPorReferenciaService;
import com.hidrored.modulos.social.dominio.Comentario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComentarioController.class)
class ComentarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AgregarComentarioService agregarComentarioService;

  @MockitoBean
  private ListarComentariosPorReferenciaService listarComentariosService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void agregarComentario_DebeRetornarComentarioCreado() throws Exception {
    // Arrange
    String referenciaId = "reporte-123";
    String usuarioId = "user-abc";
    AgregarComentarioRequest request = new AgregarComentarioRequest();
    request.setContenido("Nuevo comentario");

    Comentario comentarioCreado = new Comentario("comment-1", referenciaId, usuarioId, "Nuevo comentario",
        LocalDateTime.now());
    when(agregarComentarioService.agregarComentario(any())).thenReturn(comentarioCreado);

    // Act & Assert
    mockMvc.perform(post("/api/v1/comentarios/{referenciaId}", referenciaId)
        .header("X-Usuario-ID", usuarioId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("comment-1"))
        .andExpect(jsonPath("$.contenido").value("Nuevo comentario"));
  }

  @Test
  void listarComentarios_DebeRetornarListaDeComentarios() throws Exception {
    // Arrange
    String referenciaId = "reporte-123";
    ComentarioResponse comentario = ComentarioResponse.fromDomain(
        new Comentario("comment-1", referenciaId, "user-abc", "Test", LocalDateTime.now()));
    when(listarComentariosService.listarComentarios(referenciaId)).thenReturn(Collections.singletonList(comentario));

    // Act & Assert
    mockMvc.perform(get("/api/v1/comentarios/{referenciaId}", referenciaId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("comment-1"))
        .andExpect(jsonPath("$[0].contenido").value("Test"));
  }
}
