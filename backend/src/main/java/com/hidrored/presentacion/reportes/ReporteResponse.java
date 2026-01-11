package com.hidrored.presentacion.reportes;

import com.hidrored.modulos.reportes.infraestructura.web.ReporteDTO;
import lombok.Value;
import java.util.List;

@Value
public class ReporteResponse {
  String id;
  String usuarioId;
  String titulo;
  String descripcion;
  String estado;
  String tipo;
  String prioridad;
  String fechaCreacion;
  String fechaActualizacion;
  ImagenAdjuntaResponse imagenAdjunta;
  UbicacionResponse ubicacion;
  List<HistorialCambioResponse> historialCambios;

  public static ReporteResponse fromDto(ReporteDTO dto) {
    return new ReporteResponse(
        dto.getId(),
        dto.getUsuarioId(),
        dto.getTitulo(),
        dto.getDescripcion(),
        dto.getEstado(),
        dto.getTipo(),
        dto.getPrioridad(),
        dto.getFechaCreacion(),
        dto.getFechaActualizacion(),
        ImagenAdjuntaResponse.fromDto(dto.getImagenAdjunta()),
        UbicacionResponse.fromDto(dto.getUbicacion()),
        dto.getHistorialCambios().stream().map(HistorialCambioResponse::fromDto).toList());
  }
}
