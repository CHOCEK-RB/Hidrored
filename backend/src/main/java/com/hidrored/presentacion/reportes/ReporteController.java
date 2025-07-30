package com.hidrored.presentacion.reportes;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hidrored.aplicacion.reportes.AgregarComentarioCommand;
import com.hidrored.aplicacion.reportes.CrearReporteCommand;
import com.hidrored.aplicacion.reportes.ReporteApplicationService;
import com.hidrored.aplicacion.reportes.ReporteDTO;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

  private final ReporteApplicationService reporteService;

  public ReporteController(ReporteApplicationService reporteService) {
    this.reporteService = reporteService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReporteDTO> obtenerReportePorId(@PathVariable String id) {
    ReporteDTO reporte = reporteService.obtenerReportePorId(id);
    if (reporte != null) {
      return ResponseEntity.ok(reporte);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{reporteId}/comentarios")
  public ResponseEntity<ReporteDTO> agregarComentario(
      @PathVariable String reporteId,
      @RequestHeader("X-Usuario-ID") String usuarioId,
      @RequestBody AgregarComentarioRequest request) {

    AgregarComentarioCommand command = new AgregarComentarioCommand(reporteId, usuarioId, request.getContenido());
    ReporteDTO reporteActualizado = reporteService.agregarComentarioAReporte(command);
    return ResponseEntity.ok(reporteActualizado);
  }

  @PostMapping
  public ResponseEntity<ReporteDTO> crearReporte(
      @RequestHeader("usuarioId") String usuarioId,
      @RequestParam("titulo") String titulo,
      @RequestParam("descripcion") String descripcion,
      @RequestParam("tipo") String tipo,
      @RequestParam("prioridad") String prioridad,
      @RequestParam("latitud") Double latitud,
      @RequestParam("longitud") Double longitud,
      @RequestParam("direccion") String direccion,
      @RequestPart(value = "imagenFile", required = false) MultipartFile imagenFile) {

    CrearReporteCommand command = new CrearReporteCommand(
        usuarioId,
        titulo,
        descripcion,
        latitud,
        longitud,
        direccion,
        tipo,
        prioridad);

    ReporteDTO nuevoReporte = reporteService.crearReporte(command, imagenFile);

    return ResponseEntity.ok(nuevoReporte);
  }

  @GetMapping("/cercanos")
  public ResponseEntity<List<ReporteDTO>> obtenerReportesCercanos(
      @RequestParam("lat") double latitud,
      @RequestParam("lng") double longitud,
      @RequestParam(value = "radioKm", defaultValue = "1.0") double radioKm) {
    List<ReporteDTO> reportesCercanos = reporteService.obtenerReportesCercanos(latitud, longitud, radioKm);
    return ResponseEntity.ok(reportesCercanos);
  }

  @GetMapping("/mis-reportes")
  public ResponseEntity<List<ReporteDTO>> obtenerMisReportes(@RequestHeader("X-Usuario-ID") String usuarioId) {
    List<ReporteDTO> reportes = reporteService.obtenerReportesPorUsuario(usuarioId);
    return ResponseEntity.ok(reportes);
  }

  @GetMapping
  public ResponseEntity<List<ReporteDTO>> obtenerReportes(
      @RequestParam(value = "provincia", required = false) String provincia,
      @RequestParam(value = "distrito", required = false) String distrito) {
    List<ReporteDTO> reportes = reporteService.obtenerReportes(provincia, distrito);
    return ResponseEntity.ok(reportes);
  }
}
