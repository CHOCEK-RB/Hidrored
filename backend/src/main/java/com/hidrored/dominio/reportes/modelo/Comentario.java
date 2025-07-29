package com.hidrored.dominio.reportes.modelo;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Comentario {
  private final String id;
  private final String usuarioId;
  private final String contenido;
  private final LocalDateTime fechaCreacion;

  public Comentario(String usuarioId, String contenido) {
    this.id = UUID.randomUUID().toString();
    this.usuarioId = usuarioId;
    this.contenido = contenido;
    this.fechaCreacion = LocalDateTime.now();
  }
}
