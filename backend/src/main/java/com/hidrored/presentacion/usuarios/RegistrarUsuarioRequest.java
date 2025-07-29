package com.hidrored.presentacion.usuarios;

import lombok.Data;

@Data
public class RegistrarUsuarioRequest {
  private String nombre;
  private String email;
  private String telefono;
  private String password;
}
