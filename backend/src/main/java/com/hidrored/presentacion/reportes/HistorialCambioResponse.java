package com.hidrored.presentacion.reportes;

import com.hidrored.aplicacion.reportes.HistorialCambioDTO;
import lombok.Value;

@Value
public class HistorialCambioResponse {
  String fechaCambio;
  String descripcion;
  String usuarioIdCambio;

  public static HistorialCambioResponse fromDto(HistorialCambioDTO dto) {
    return new HistorialCambioResponse(dto.getFechaCambio(), dto.getDescripcion(), dto.getUsuarioIdCambio());
  }
}
