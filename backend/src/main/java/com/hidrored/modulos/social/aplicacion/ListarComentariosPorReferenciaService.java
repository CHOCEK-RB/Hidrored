package com.hidrored.modulos.social.aplicacion;

import com.hidrored.modulos.social.dominio.IComentarioRepository;
import com.hidrored.modulos.social.infraestructura.web.ComentarioResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarComentariosPorReferenciaService {

  private final IComentarioRepository comentarioRepository;

  public ListarComentariosPorReferenciaService(IComentarioRepository comentarioRepository) {
    this.comentarioRepository = comentarioRepository;
  }

  @Transactional(readOnly = true)
  public List<ComentarioResponse> listarComentarios(String referenciaId) {
    if (referenciaId == null || referenciaId.trim().isEmpty()) {
      throw new IllegalArgumentException("El ID de referencia no puede estar vacío.");
    }

    return comentarioRepository.findByReferenciaIdOrderByFechaCreacionDesc(referenciaId)
        .stream()
        .map(ComentarioResponse::fromDomain)
        .collect(Collectors.toList());
  }
}
