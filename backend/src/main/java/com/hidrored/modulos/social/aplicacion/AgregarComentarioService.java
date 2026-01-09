package com.hidrored.modulos.social.aplicacion;

import com.hidrored.modulos.social.dominio.Comentario;
import com.hidrored.modulos.social.dominio.IComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgregarComentarioService {

  private final IComentarioRepository comentarioRepository;

  public AgregarComentarioService(IComentarioRepository comentarioRepository) {
    this.comentarioRepository = comentarioRepository;
  }

  @Transactional
  public Comentario agregarComentario(AgregarComentarioCommand command) {
    if (command.getContenido() == null || command.getContenido().trim().isEmpty()) {
      throw new IllegalArgumentException("El contenido del comentario no puede estar vacío.");
    }
    if (command.getUsuarioId() == null || command.getUsuarioId().trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de usuario no puede estar vacío.");
    }
    if (command.getReferenciaId() == null || command.getReferenciaId().trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de referencia no puede estar vacío.");
    }
    // TODO: Validate that the 'referenciaId' actually points to an existing entity
    // (e.g., Report)

    Comentario nuevoComentario = new Comentario(
        command.getReferenciaId(),
        command.getUsuarioId(),
        command.getContenido());

    return comentarioRepository.save(nuevoComentario);
  }
}
