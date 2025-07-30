package com.hidrored.presentacion.usuarios;

import lombok.Data;

@Data
public class UsuarioResponse {
  private String id;
  private String nombre;
  private String email;
  private String telefono;
}
