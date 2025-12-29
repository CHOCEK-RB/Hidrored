package com.hidrored.usuarios.infraestructura;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegistrarUsuarioRequest {
  private String nombre;
  private String email;
  private String telefono;
  private String password;
}
