package com.hidrored.modulos.usuarios.infraestructura.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.hidrored.modulos.usuarios.dominio.Usuario;

class UsuarioDTOTest {

  @Test
  void fromDomain_mapsFieldsCorrectly() {
    Usuario usuario = new Usuario("Nombre", "mail@example.com", "9999", "pwd");

    UsuarioDTO dto = UsuarioDTO.fromDomain(usuario);

    assertEquals(usuario.getId(), dto.getId());
    assertEquals(usuario.getNombre(), dto.getNombre());
    assertEquals(usuario.getEmail(), dto.getEmail());
    assertEquals(usuario.getTelefono(), dto.getTelefono());
  }
}
