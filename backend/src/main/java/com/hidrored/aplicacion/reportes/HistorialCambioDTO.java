package com.hidrored.aplicacion.reportes;

import lombok.Getter;
import java.time.format.DateTimeFormatter;

import com.hidrored.modulos.reportes.dominio.modelo.HistorialCambio;

@Getter
public class HistorialCambioDTO {
  private final String fechaCambio;
  private final String descripcion;
  private final String usuarioIdCambio;

  private HistorialCambioDTO(HistorialCambio historial) {
    this.fechaCambio = historial.getFechaCambio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    this.descripcion = historial.getDescripcion();
    this.usuarioIdCambio = historial.getUsuarioIdCambio();
  }

  public static HistorialCambioDTO fromDomain(HistorialCambio historial) {
    return new HistorialCambioDTO(historial);
  }
}
