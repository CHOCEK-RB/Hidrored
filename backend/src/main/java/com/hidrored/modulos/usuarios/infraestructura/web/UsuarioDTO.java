package com.hidrored.modulos.usuarios.infraestructura.web;

import com.hidrored.modulos.usuarios.dominio.Usuario;

import lombok.Getter; 

@Getter
public class UsuarioDTO {

  private final String id;
  private final String nombre;
  private final String email;
  private final String telefono;

  private UsuarioDTO(String id, String nombre, String email, String telefono) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
  }

  /**
   * Método de fábrica (factory method) para convertir una entidad de dominio
   * a un DTO. Este es el método que faltaba.
   *
   * @param usuario La entidad de dominio Usuario.
   * @return Un nuevo objeto UsuarioDTO.
   */
  public static UsuarioDTO fromDomain(Usuario usuario) {
    return new UsuarioDTO(
        usuario.getId(),
        usuario.getNombre(),
        usuario.getEmail(),
        usuario.getTelefono());
  }
}
