package com.hidrored.aplicacion.reportes;

import com.hidrored.dominio.reportes.modelo.Reporte;
import lombok.Getter;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
  private final ImagenAdjuntaDTO imagenAdjunta;
  private final UbicacionDTO ubicacion;
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
    this.imagenAdjunta = ImagenAdjuntaDTO.fromDomain(reporte.getImagenAdjunta());

    if (reporte.getLocation() != null && reporte.getLocation().length >= 2) {
      this.ubicacion = new UbicacionDTO(
          reporte.getLocation()[1],
          reporte.getLocation()[0],
          reporte.getDireccion());
    } else {
      this.ubicacion = null;
    }

    this.historialCambios = reporte.getHistorialCambios().stream()
        .map(HistorialCambioDTO::fromDomain)
        .toList();
  }

  public static ReporteDTO fromDomain(Reporte reporte) {
    return new ReporteDTO(reporte);
  }
}
