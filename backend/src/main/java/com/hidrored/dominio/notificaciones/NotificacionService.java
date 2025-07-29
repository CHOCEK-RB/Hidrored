package com.hidrored.dominio.notificaciones;

import com.hidrored.dominio.notificaciones.modelo.Notificacion;
import com.hidrored.dominio.usuarios.modelo.ID;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class NotificacionService {

    /**
     * Default constructor
     */
    public NotificacionService() {
    }

    /**
     * 
     */
    private INotificacionRepository notificacionRepository;

    /**
     * @param usuarioId 
     * @param mensaje 
     * @return
     */
    public Notificacion crearNotificacion(ID usuarioId, String mensaje) {
        // TODO implement here
        return null;
    }

    /**
     * @param notificacionId
     */
    public void marcarNotificacionComoLeida(ID notificacionId) {
        // TODO implement here
    }

    /**
     * @param notificacion
     */
    public void enviarNotificacion(Notificacion notificacion) {
        // TODO implement here
    }

}