package com.hidrored.aplicacion.reportes;

import com.hidrored.dominio.reportes.IReporteRepository;
import com.hidrored.dominio.reportes.modelo.*;
import com.hidrored.dominio.usuarios.IUsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteApplicationService {

  private final IReporteRepository reporteRepository;
  private final IUsuarioRepository usuarioRepository;

  public ReporteApplicationService(IReporteRepository reporteRepository, IUsuarioRepository usuarioRepository) {
    this.reporteRepository = reporteRepository;
    this.usuarioRepository = usuarioRepository;
  }

  @Transactional
  public ReporteDTO crearReporte(CrearReporteCommand command) {
    usuarioRepository.findById(command.getUsuarioId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + command.getUsuarioId()));

    Ubicacion ubicacion = new Ubicacion(command.getLatitud(), command.getLongitud(), command.getDireccion());

    Reporte nuevoReporte = new Reporte(
        command.getUsuarioId(),
        command.getTitulo(),
        command.getDescripcion(),
        ubicacion,
        TipoReporte.valueOf(command.getTipo().toUpperCase()),
        PrioridadReporte.valueOf(command.getPrioridad().toUpperCase()));

    Reporte reporteGuardado = reporteRepository.save(nuevoReporte);
    return ReporteDTO.fromDomain(reporteGuardado);
  }

  @Transactional
  public ReporteDTO agregarComentarioAReporte(AgregarComentarioCommand command) {
    Reporte reporte = reporteRepository.findById(command.getReporteId())
        .orElseThrow(() -> new IllegalStateException("Reporte no encontrado"));

    usuarioRepository.findById(command.getUsuarioId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    reporte.agregarComentario(command.getUsuarioId(), command.getContenido());

    Reporte reporteActualizado = reporteRepository.save(reporte);
    return ReporteDTO.fromDomain(reporteActualizado);
  }

  @Transactional
  public ReporteDTO actualizarEstadoReporte(ActualizarEstadoReporteCommand command) {
    Reporte reporte = reporteRepository.findById(command.getReporteId())
        .orElseThrow(() -> new IllegalStateException("Reporte no encontrado"));

    usuarioRepository.findById(command.getUsuarioIdCambio())
        .orElseThrow(() -> new IllegalArgumentException("Usuario que realiza el cambio no encontrado"));

    EstadoReporte nuevoEstado = EstadoReporte.valueOf(command.getNuevoEstado().toUpperCase());

    reporte.actualizarEstado(nuevoEstado, command.getUsuarioIdCambio(), command.getMotivo());

    Reporte reporteActualizado = reporteRepository.save(reporte);
    return ReporteDTO.fromDomain(reporteActualizado);
  }

  @Transactional(readOnly = true)
  public List<ReporteDTO> obtenerReportesPorUsuario(String usuarioId) {
    return reporteRepository.findByUsuarioId(usuarioId).stream()
        .map(ReporteDTO::fromDomain)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ReporteDTO obtenerReportePorId(String reporteId) {
    return reporteRepository.findById(reporteId)
        .map(ReporteDTO::fromDomain)
        .orElse(null);
  }
}
