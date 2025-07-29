package com.hidrored.dominio.reportes.modelo;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "reportes")
@Getter
public class Reporte {

  @Id
  private String id;
  private String usuarioId;
  private String titulo;
  private String descripcion;
  private Ubicacion ubicacion;
  private EstadoReporte estado;
  private TipoReporte tipo;
  private PrioridadReporte prioridad;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaActualizacion;
  private List<Comentario> comentarios = new ArrayList<>();
  private List<HistorialCambio> historialCambios = new ArrayList<>();

  public Reporte(String usuarioId, String titulo, String descripcion, Ubicacion ubicacion, TipoReporte tipo,
      PrioridadReporte prioridad) {
    if (usuarioId == null || titulo == null || titulo.trim().isEmpty() || descripcion == null
        || descripcion.trim().isEmpty()) {
      throw new IllegalArgumentException("Los campos del reporte no pueden ser nulos.");
    }
    this.id = UUID.randomUUID().toString();
    this.usuarioId = usuarioId;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.ubicacion = ubicacion;
    this.estado = EstadoReporte.PENDIENTE;
    this.tipo = tipo;
    this.prioridad = prioridad;
    this.fechaCreacion = LocalDateTime.now();
    this.fechaActualizacion = LocalDateTime.now();
    this.historialCambios.add(new HistorialCambio(LocalDateTime.now(), "Reporte creado.", usuarioId));
  }

  private Reporte() {
  }

  public void agregarComentario(String usuarioId, String contenido) {
    this.comentarios.add(new Comentario(usuarioId, contenido));
    this.fechaActualizacion = LocalDateTime.now();
  }

  public void actualizarEstado(EstadoReporte nuevoEstado, String usuarioIdCambio, String motivo) {
    String descripcionCambio = String.format("Estado cambiado de %s a %s. Motivo: %s", this.estado, nuevoEstado,
        motivo);
    this.historialCambios.add(new HistorialCambio(LocalDateTime.now(), descripcionCambio, usuarioIdCambio));
    this.estado = nuevoEstado;
    this.fechaActualizacion = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Reporte reporte = (Reporte) o;
    return Objects.equals(id, reporte.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
