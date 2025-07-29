package com.hidrored.aplicacion.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgregarComentarioCommand {

  private final String reporteId;

  private final String usuarioId;
  private final String contenido;
}
