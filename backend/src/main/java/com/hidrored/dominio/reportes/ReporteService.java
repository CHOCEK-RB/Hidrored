package com.hidrored.dominio.reportes;

import com.hidrored.dominio.reportes.modelo.EstadoReporte;
import com.hidrored.dominio.reportes.modelo.ImagenAdjunta;
import com.hidrored.dominio.reportes.modelo.PrioridadReporte;
import com.hidrored.dominio.reportes.modelo.Reporte;
import com.hidrored.dominio.reportes.modelo.TipoReporte;
import com.hidrored.dominio.reportes.modelo.Ubicacion;
import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.ID;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ReporteService {

    /**
     * Default constructor
     */
    public ReporteService() {
    }

    /**
     * 
     */
    private IUsuarioRepository usuarioRepository;

    /**
     * 
     */
    public IReporteRepository reporteRepository;

    /**
     * @param usuarioId 
     * @param titulo 
     * @param descripcion 
     * @param ubicacion 
     * @param tipo 
     * @param prioridad 
     * @return
     */
    public Reporte crearNuevoReporte(ID usuarioId, String titulo, String descripcion, Ubicacion ubicacion, TipoReporte tipo, PrioridadReporte prioridad) {
        // TODO implement here
        return null;
    }

    /**
     * @param reporteId 
     * @param nuevoEstado 
     * @param usuarioIdCambio 
     * @param motivo 
     * @return
     */
    public Reporte procesarCambioEstadoReporte(ID reporteId, EstadoReporte nuevoEstado, ID usuarioIdCambio, String motivo) {
        // TODO implement here
        return null;
    }

    /**
     * @param reporteId 
     * @param usuarioId 
     * @param contenido 
     * @return
     */
    public Reporte agregarComentarioAReporte(ID reporteId, ID usuarioId, String contenido) {
        // TODO implement here
        return null;
    }

    /**
     * @param reporteId 
     * @param imagen 
     * @return
     */
    public Reporte agregarImagenAdjuntaAReporte(ID reporteId, ImagenAdjunta imagen) {
        // TODO implement here
        return null;
    }

}