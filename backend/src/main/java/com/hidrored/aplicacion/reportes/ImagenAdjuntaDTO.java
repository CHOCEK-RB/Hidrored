package com.hidrored.aplicacion.reportes;

import com.hidrored.modulos.reportes.dominio.ImagenAdjunta;
import lombok.Getter;
import java.time.format.DateTimeFormatter;

@Getter
public class ImagenAdjuntaDTO {
  private final String url;
  private final String nombreArchivo;
  private final String tipoMime;
  private final Long tamanioBytes;
  private final String fechaSubida;

  private ImagenAdjuntaDTO(ImagenAdjunta imagen) {
    this.url = imagen.getUrl();
    this.nombreArchivo = imagen.getNombreArchivo();
    this.tipoMime = imagen.getTipoMime();
    this.tamanioBytes = imagen.getTamanioBytes();
    this.fechaSubida = imagen.getFechaSubida().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static ImagenAdjuntaDTO fromDomain(ImagenAdjunta imagen) {
    if (imagen == null)
      return null;
    return new ImagenAdjuntaDTO(imagen);
  }
}
