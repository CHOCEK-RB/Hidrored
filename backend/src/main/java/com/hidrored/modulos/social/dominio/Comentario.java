package com.hidrored.modulos.social.dominio;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Comentario {
  private final String id;
  private final String referenciaId;
  private final String usuarioId;
  private final String contenido;
  private final LocalDateTime fechaCreacion;

  public Comentario(String referenciaId, String usuarioId, String contenido) {
    this.id = UUID.randomUUID().toString();
    this.referenciaId = referenciaId;
    this.usuarioId = usuarioId;
    this.contenido = contenido;
    this.fechaCreacion = LocalDateTime.now();
  }

  @PersistenceCreator
  public Comentario(String id, String referenciaId, String usuarioId, String contenido, LocalDateTime fechaCreacion) {
    this.id = id;
    this.referenciaId = referenciaId;
    this.usuarioId = usuarioId;
    this.contenido = contenido;
    this.fechaCreacion = fechaCreacion;
  }
}
