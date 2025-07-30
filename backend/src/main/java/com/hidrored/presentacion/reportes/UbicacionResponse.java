package com.hidrored.presentacion.reportes;

import com.hidrored.aplicacion.reportes.UbicacionDTO;
import lombok.Value;

@Value
public class UbicacionResponse {
  Double latitud;
  Double longitud;
  String direccion;

  public static UbicacionResponse fromDto(UbicacionDTO dto) {
    if (dto == null)
      return null;
    return new UbicacionResponse(dto.getLatitud(), dto.getLongitud(), dto.getDireccion());
  }
}
