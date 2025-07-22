package com.hidrored.aplicacion.Reportes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActualizarEstadoReporteCommand {
  private final String reporteId;

  private final String nuevoEstado;
  private final String usuarioIdCambio;
  private final String motivo;
}
