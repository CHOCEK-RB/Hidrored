package com.hidrored.dominio.notificaciones;

import com.hidrored.dominio.notificaciones.modelo.Notificacion;
import com.hidrored.dominio.usuarios.modelo.ID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificacionRepository extends MongoRepository<Notificacion, ID> {

    /**
     * Busca todas las notificaciones para un usuario específico.
     * @param usuarioId El ID del usuario.
     * @return Una lista de notificaciones para ese usuario.
     */
    List<Notificacion> findByUsuarioId(ID usuarioId);

    /**
     * Busca notificaciones para un usuario, filtrando por estado de lectura.
     * @param usuarioId El ID del usuario.
     * @param leida true para buscar leídas, false para no leídas.
     * @return Una lista de notificaciones que coinciden con los criterios.
     */
    List<Notificacion> findByUsuarioIdAndLeida(ID usuarioId, boolean leida);
}
