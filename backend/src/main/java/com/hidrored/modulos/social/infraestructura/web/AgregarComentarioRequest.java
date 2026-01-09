package com.hidrored.modulos.social.infraestructura.web;

import lombok.Data;

@Data
public class AgregarComentarioRequest {

  private String contenido;

  public String getContenido() {
    return contenido;
  }
}
