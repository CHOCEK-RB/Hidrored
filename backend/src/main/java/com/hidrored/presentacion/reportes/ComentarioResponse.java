package com.hidrored.presentacion.reportes;

import com.hidrored.modulos.reportes.infraestructura.web.ComentarioDTO;
import lombok.Value;

@Value
public class ComentarioResponse {
  String id;
  String usuarioId;
  String contenido;
  String fechaCreacion;

  public static ComentarioResponse fromDto(ComentarioDTO dto) {
    return new ComentarioResponse(dto.getId(), dto.getUsuarioId(), dto.getContenido(), dto.getFechaCreacion());
  }
}
