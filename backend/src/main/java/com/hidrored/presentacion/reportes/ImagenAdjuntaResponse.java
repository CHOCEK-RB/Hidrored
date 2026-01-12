package com.hidrored.presentacion.reportes;

import com.hidrored.modulos.reportes.infraestructura.web.ImagenAdjuntaDTO;
import lombok.Value;

@Value
public class ImagenAdjuntaResponse {
  String url;
  String nombreArchivo;
  String tipoMime;
  Long tamanioBytes;
  String fechaSubida;

  public static ImagenAdjuntaResponse fromDto(ImagenAdjuntaDTO dto) {
    if (dto == null)
      return null;
    return new ImagenAdjuntaResponse(dto.getUrl(), dto.getNombreArchivo(), dto.getTipoMime(), dto.getTamanioBytes(),
        dto.getFechaSubida());
  }
}
