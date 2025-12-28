package com.hidrored.shared.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "url")
public class ImagenAdjunta {

  private String url;
  private String nombreArchivo;
  private String tipoMime;
  private Long tamanioBytes;
  private LocalDateTime fechaSubida;

  @PersistenceCreator
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
}
