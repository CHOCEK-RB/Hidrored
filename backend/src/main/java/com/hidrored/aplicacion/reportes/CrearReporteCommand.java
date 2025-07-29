package com.hidrored.aplicacion.reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CrearReporteCommand {

  private final String usuarioId;
  private final String titulo;
  private final String descripcion;
  private final Double latitud;
  private final Double longitud;
  private final String direccion;
  private final String tipo;
  private final String prioridad;

}
