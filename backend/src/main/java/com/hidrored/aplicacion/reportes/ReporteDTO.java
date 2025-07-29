package com.hidrored.aplicacion.reportes;

import lombok.Getter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.hidrored.dominio.reportes.modelo.Reporte;

@Getter
public class ReporteDTO {
  private final String id;
  private final String usuarioId;
  private final String titulo;
  private final String descripcion;
  private final String estado;
  private final String tipo;
  private final String prioridad;
  private final String fechaCreacion;
  private final String fechaActualizacion;
  private final UbicacionDTO ubicacion;
  private final List<ComentarioDTO> comentarios;
  private final List<HistorialCambioDTO> historialCambios;

  private ReporteDTO(Reporte reporte) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    this.id = reporte.getId();
    this.usuarioId = reporte.getUsuarioId();
    this.titulo = reporte.getTitulo();
    this.descripcion = reporte.getDescripcion();
    this.estado = reporte.getEstado().name();
    this.tipo = reporte.getTipo().name();
    this.prioridad = reporte.getPrioridad().name();
    this.fechaCreacion = reporte.getFechaCreacion().format(formatter);
    this.fechaActualizacion = reporte.getFechaActualizacion().format(formatter);
    this.ubicacion = UbicacionDTO.fromDomain(reporte.getUbicacion());
    this.comentarios = reporte.getComentarios().stream()
        .map(ComentarioDTO::fromDomain)
        .collect(Collectors.toList());
    this.historialCambios = reporte.getHistorialCambios().stream()
        .map(HistorialCambioDTO::fromDomain)
        .collect(Collectors.toList());
  }

  public static ReporteDTO fromDomain(Reporte reporte) {
    return new ReporteDTO(reporte);
  }
}
