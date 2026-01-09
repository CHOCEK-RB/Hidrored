package com.hidrored.modulos.social.infraestructura.web;

import com.hidrored.modulos.social.dominio.Comentario;
import lombok.Value;

import java.time.format.DateTimeFormatter;

@Value
public class ComentarioResponse {
  String id;
  String referenciaId;
  String usuarioId;
  String contenido;
  String fechaCreacion;

  public static ComentarioResponse fromDomain(Comentario comentario) {
    return new ComentarioResponse(
        comentario.getId(),
        comentario.getReferenciaId(),
        comentario.getUsuarioId(),
        comentario.getContenido(),
        comentario.getFechaCreacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }
}
