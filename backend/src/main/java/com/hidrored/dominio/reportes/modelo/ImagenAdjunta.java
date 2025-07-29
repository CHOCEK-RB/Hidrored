package com.hidrored.dominio.reportes.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class ImagenAdjunta {

  private String url;
  private String nombreArchivo;
  private String tipoMime;
  private Long tamanioBytes;
  private LocalDateTime fechaSubida;

  public ImagenAdjunta(String url, String nombreArchivo, String tipoMime, Long tamanioBytes,
      LocalDateTime fechaSubida) {
    if (url == null || url.trim().isEmpty() || nombreArchivo == null || nombreArchivo.trim().isEmpty() ||
        tipoMime == null || tipoMime.trim().isEmpty() || tamanioBytes == null || tamanioBytes <= 0
        || fechaSubida == null) {
      throw new IllegalArgumentException("Todos los campos de ImagenAdjunta son obligatorios y válidos.");
    }
    this.url = url;
    this.nombreArchivo = nombreArchivo;
    this.tipoMime = tipoMime;
    this.tamanioBytes = tamanioBytes;
    this.fechaSubida = fechaSubida;
  }

  public String getUrl() {
    return url;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public String getTipoMime() {
    return tipoMime;
  }

  public Long getTamanioBytes() {
    return tamanioBytes;
  }

  public LocalDateTime getFechaSubida() {
    return fechaSubida;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ImagenAdjunta that = (ImagenAdjunta) o;
    return Objects.equals(url, that.url) &&
        Objects.equals(nombreArchivo, that.nombreArchivo) &&
        Objects.equals(tipoMime, that.tipoMime) &&
        Objects.equals(tamanioBytes, that.tamanioBytes) &&
        Objects.equals(fechaSubida, that.fechaSubida);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, nombreArchivo, tipoMime, tamanioBytes, fechaSubida);
  }

  @Override
  public String toString() {
    return "ImagenAdjunta{" +
        "url='" + url + '\'' +
        ", nombreArchivo='" + nombreArchivo + '\'' +
        ", tipoMime='" + tipoMime + '\'' +
        ", tamanioBytes=" + tamanioBytes +
        ", fechaSubida=" + fechaSubida +
        '}';
  }
}
