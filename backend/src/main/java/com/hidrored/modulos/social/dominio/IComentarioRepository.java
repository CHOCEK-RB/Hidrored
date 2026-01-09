package com.hidrored.modulos.social.dominio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IComentarioRepository extends MongoRepository<Comentario, String> {

  /**
   * Busca y devuelve todos los comentarios asociados a una referencia específica
   * (como un reporte),
   * ordenados por fecha de creación descendente (los más nuevos primero).
   *
   * @param referenciaId El ID de la entidad a la que pertenecen los comentarios.
   * @return Una lista de comentarios ordenados.
   */
  List<Comentario> findByReferenciaIdOrderByFechaCreacionDesc(String referenciaId);
}
