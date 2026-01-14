package com.hidrored.modulos.reportes.infraestructura.web;

import com.hidrored.modulos.social.dominio.Comentario;
import lombok.Getter;
import java.time.format.DateTimeFormatter;

@Getter
public class ComentarioDTO {
  private final String id;
  private final String referenciaId;
  private final String usuarioId;
  private final String contenido;
  private final String fechaCreacion;

  private ComentarioDTO(Comentario comentario) {
    this.id = comentario.getId();
    this.referenciaId = comentario.getReferenciaId();
    this.usuarioId = comentario.getUsuarioId();
    this.contenido = comentario.getContenido();
    this.fechaCreacion = comentario.getFechaCreacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static ComentarioDTO fromDomain(Comentario comentario) {
    return new ComentarioDTO(comentario);
  }
}
