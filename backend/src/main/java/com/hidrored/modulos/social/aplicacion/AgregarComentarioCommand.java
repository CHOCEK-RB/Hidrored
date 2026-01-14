package com.hidrored.modulos.social.aplicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgregarComentarioCommand {

  private final String referenciaId;
  private final String tipoReferencia; // e.g., "REPORTE", "USUARIO", etc.
  private final String usuarioId;
  private final String contenido;
}
