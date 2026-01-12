package com.hidrored.modulos.reportes.aplicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubirImagenAdjuntaCommand {

  private final String reporteId;

  private final String nombreArchivo;
  private final String tipoMime;
  private final Long tamanioBytes;
  private final String urlImagen;

}
