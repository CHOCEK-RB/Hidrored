package com.hidrored.presentacion.reportes;

import lombok.Data;

@Data
public class ActualizarEstadoReporteRequest {

  private String nuevoEstado;
  private String motivo;

  public String getNuevoEstado() {
    return nuevoEstado;
  }

  public String getMotivo() {
    return motivo;
  }
}
