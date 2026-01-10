package com.hidrored.modulos.usuarios.infraestructura.web;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
