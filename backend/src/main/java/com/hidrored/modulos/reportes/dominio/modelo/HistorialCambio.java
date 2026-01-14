package com.hidrored.modulos.reportes.dominio.modelo;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class HistorialCambio {
  private final LocalDateTime fechaCambio;
  private final String descripcion;
  private final String usuarioIdCambio;

  public HistorialCambio(LocalDateTime fechaCambio, String descripcion, String usuarioIdCambio) {
    this.fechaCambio = fechaCambio;
    this.descripcion = descripcion;
    this.usuarioIdCambio = usuarioIdCambio;
  }
}
