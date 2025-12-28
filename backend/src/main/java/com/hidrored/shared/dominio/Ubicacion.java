package com.hidrored.shared.dominio;

import java.util.Objects;

public class Ubicacion {

  private Double latitud;
  private Double longitud;
  private String direccion;

  public Ubicacion(Double latitud, Double longitud, String direccion) {

    if (latitud == null || longitud == null || direccion == null || direccion.trim().isEmpty()) {
      throw new IllegalArgumentException("Latitud, longitud y dirección no pueden ser nulos o vacíos.");
    }
    this.latitud = latitud;
    this.longitud = longitud;
    this.direccion = direccion;
  }

  public Double getLatitud() {
    return latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public String getDireccion() {
    return direccion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Ubicacion ubicacion = (Ubicacion) o;
    return Objects.equals(latitud, ubicacion.latitud) &&
        Objects.equals(longitud, ubicacion.longitud) &&
        Objects.equals(direccion, ubicacion.direccion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitud, longitud, direccion);
  }

  @Override
  public String toString() {
    return "Ubicacion{" +
        "latitud=" + latitud +
        ", longitud=" + longitud +
        ", direccion='" + direccion + '\'' +
        '}';
  }
}
