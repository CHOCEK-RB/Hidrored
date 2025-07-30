package com.hidrored.presentacion.reportes;

import lombok.Data;

@Data
public class AgregarComentarioRequest {

  private String contenido;

  public String getContenido() {
    return contenido;
  }
}
