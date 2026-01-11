package com.hidrored.modulos.usuarios.infraestructura.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;
import com.hidrored.modulos.usuarios.aplicacion.UsuarioApplicationService;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private UsuarioApplicationService usuarioService;

  @Test
  void registrarUsuario_endpoint_returnsOk() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("nombre", "Test");
    req.put("email", "t@test.com");
    req.put("telefono", "555");
    req.put("password", "p");

    when(usuarioService.registrarUsuario(any())).thenReturn(
        UsuarioDTO.fromDomain(new com.hidrored.modulos.usuarios.dominio.Usuario("Test", "t@test.com", "555", "h")));

    mockMvc.perform(post("/api/usuarios/registro").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req))).andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("t@test.com"));
  }

  @Test
  void login_endpoint_returnsUnauthorized_onInvalidCredentials() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("email", "x@x.com");
    req.put("password", "bad");

    when(usuarioService.autenticarUsuario(any(), any())).thenThrow(new SecurityException("Credenciales inválidas"));

    mockMvc.perform(post("/api/usuarios/login").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req))).andExpect(status().isUnauthorized());
  }
}
