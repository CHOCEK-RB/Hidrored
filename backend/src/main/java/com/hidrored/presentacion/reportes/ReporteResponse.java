package com.hidrored.presentacion.reportes;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ReporteResponse {

    /**
     * Default constructor
     */
    public ReporteResponse() {
    }

    /**
     * 
     */
    public String id;

    /**
     * 
     */
    public String usuarioId;

    /**
     * 
     */
    public String titulo;

    /**
     * 
     */
    public String descripcion;

    /**
     * 
     */
    public UbicacionResponse ubicacion;

    /**
     * 
     */
    public String estado;

    /**
     * 
     */
    public String tipo;

    /**
     * 
     */
    public String prioridad;

    /**
     * 
     */
    public String fechaCreacion;

    /**
     * 
     */
    public String fechaActualizacion;

    /**
     * 
     */
    public List<HistorialCambioResponse> historialCambios;

    /**
     * 
     */
    public List<ImagenAdjuntaResponse> imagenesAdjuntas;

    /**
     * 
     */
    public List<ComentarioResponse> comentarios;

}