package com.hidrored.modulos.reportes.infraestructura.web;

import com.hidrored.modulos.reportes.dominio.Ubicacion;

import lombok.Getter;

@Getter
public class UbicacionDTO {
  private final Double latitud;
  private final Double longitud;
  private final String direccion;

  public UbicacionDTO(Double latitud, Double longitud, String direccion) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.direccion = direccion;
  }

  public static UbicacionDTO fromDomain(Ubicacion ubicacion) {
    return new UbicacionDTO(ubicacion.getLatitud(), ubicacion.getLongitud(), ubicacion.getDireccion());
  }
}
