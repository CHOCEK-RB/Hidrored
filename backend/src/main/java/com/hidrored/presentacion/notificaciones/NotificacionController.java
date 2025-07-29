package com.hidrored.presentacion.notificaciones;

import java.io.*;
import java.util.*;

import com.hidrored.aplicacion.notificaciones.NotificacionApplicationService;

public class NotificacionController {

    /**
     * Default constructor
     */
    public NotificacionController() {
    }

    /**
     * @param @RequestHeader usuarioId 
     * @param @RequestBody request 
     * @return
     */
    public NotificacionResponse crearNotificacion(String usuarioId, CrearNotificacionRequest request) {
        // TODO implement here
        return null;
    }

    /**
     * @param @PathVariable id 
     * @param @RequestHeader usuarioId 
     * @return
     */
    public void marcarNotificacionLeida(String id, String usuarioId) {
        // TODO implement here
    }

    /**
     * @param @PathVariable usuarioId 
     * @return
     */
    public List<NotificacionResponse> obtenerNotificacionesPorUsuario(String usuarioId) {
        // TODO implement here
        return null;
    }

}