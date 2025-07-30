package com.hidrored.aplicacion.reportes;

import lombok.Getter;
import java.time.format.DateTimeFormatter;

import com.hidrored.dominio.reportes.modelo.Comentario;

@Getter
public class ComentarioDTO {
  private final String id;
  private final String usuarioId;
  private final String contenido;
  private final String fechaCreacion;

  private ComentarioDTO(Comentario comentario) {
    this.id = comentario.getId();
    this.usuarioId = comentario.getUsuarioId();
    this.contenido = comentario.getContenido();
    this.fechaCreacion = comentario.getFechaCreacion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static ComentarioDTO fromDomain(Comentario comentario) {
    return new ComentarioDTO(comentario);
  }
}
