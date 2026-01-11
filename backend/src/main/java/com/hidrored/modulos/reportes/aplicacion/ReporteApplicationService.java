package com.hidrored.modulos.reportes.aplicacion;

import tools.jackson.databind.JsonNode;

import com.hidrored.modulos.reportes.dominio.IReporteRepository;
import com.hidrored.modulos.reportes.dominio.modelo.*;
import com.hidrored.modulos.usuarios.dominio.IUsuarioRepository;
import com.hidrored.modulos.reportes.dominio.ImagenAdjunta;
import com.hidrored.modulos.reportes.dominio.Ubicacion;
import com.hidrored.modulos.storage.dominio.IStorageService; // Added
import com.hidrored.aplicacion.reportes.CrearReporteCommand; // Added
import com.hidrored.aplicacion.reportes.ReporteDTO; // Added

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class ReporteApplicationService {

  private final IReporteRepository reporteRepository;
  private final IUsuarioRepository usuarioRepository;
  private final IStorageService fileStorageService;
  private final RestTemplate restTemplate;

  public ReporteApplicationService(IReporteRepository reporteRepository, IUsuarioRepository usuarioRepository,
      IStorageService fileStorageService, RestTemplate restTemplate) {
    this.reporteRepository = reporteRepository;
    this.usuarioRepository = usuarioRepository;
    this.fileStorageService = fileStorageService;
    this.restTemplate = restTemplate;
  }

  @Transactional
  public ReporteDTO crearReporte(CrearReporteCommand command, MultipartFile imagenFile) {
    usuarioRepository.findById(command.getUsuarioId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + command.getUsuarioId()));

    Reporte nuevoReporte = new Reporte(
        command.getUsuarioId(),
        command.getTitulo(),
        command.getDescripcion(),
        new Ubicacion(command.getLongitud(), command.getLatitud(), command.getDescripcion()),
        TipoReporte.valueOf(command.getTipo().toUpperCase()),
        PrioridadReporte.valueOf(command.getPrioridad().toUpperCase()));

    try {
      String url = String.format("https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=%s&lon=%s",
          command.getLatitud(), command.getLongitud());
      JsonNode response = restTemplate.getForObject(url, JsonNode.class);
      if (response != null && response.has("address")) {
        JsonNode address = response.get("address");

        String distrito;
        if (address.has("city_district")) {
          distrito = address.get("city_district").asString();
        } else if (address.has("suburb")) {
          distrito = address.get("suburb").asString();
        } else {
          distrito = "No disponible";
        }

        String provincia = address.has("state") ? address.get("state").asString() : "No disponible";

        nuevoReporte.setDistrito(distrito);
        nuevoReporte.setProvincia(provincia);
      }
    } catch (Exception e) {
      nuevoReporte.setDistrito("Error al obtener");
      nuevoReporte.setProvincia("Error al obtener");
    }

    if (imagenFile != null && !imagenFile.isEmpty()) {
      String imageUrl = fileStorageService.guardar(imagenFile);
      nuevoReporte.setImagenAdjunta(new ImagenAdjunta(imageUrl, imagenFile.getOriginalFilename(),
          imagenFile.getContentType(), imagenFile.getSize(), LocalDateTime.now()));
    }

    Reporte reporteGuardado = reporteRepository.save(nuevoReporte);
    return ReporteDTO.fromDomain(reporteGuardado);
  }

  @Transactional(readOnly = true)
  public List<ReporteDTO> obtenerReportesCercanos(double latitud, double longitud, double radioKm) {
    final double RADIO_TERRESTRE_KM = 6378.1;
    double radioEnRadianes = radioKm / RADIO_TERRESTRE_KM;

    return reporteRepository.executeGeoSearch(longitud, latitud, radioEnRadianes).stream()
        .map(ReporteDTO::fromDomain)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<ReporteDTO> obtenerTodosLosReportes() {
    return reporteRepository.findAll().stream()
        .map(ReporteDTO::fromDomain)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<ReporteDTO> obtenerReportes(String provincia, String distrito) {
    List<Reporte> reportes;

    boolean hasProvincia = provincia != null && !provincia.isEmpty();
    boolean hasDistrito = distrito != null && !distrito.isEmpty();

    if (hasProvincia && hasDistrito) {
      reportes = reporteRepository.findByProvinciaIgnoreCaseAndDistritoIgnoreCase(provincia, distrito);
    } else if (hasProvincia) {
      reportes = reporteRepository.findByProvinciaIgnoreCase(provincia);
    } else if (hasDistrito) {
      reportes = reporteRepository.findByDistritoIgnoreCase(distrito);
    } else {
      reportes = reporteRepository.findAll();
    }

    return reportes.stream()
        .map(ReporteDTO::fromDomain)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<ReporteDTO> obtenerReportesPorUsuario(String usuarioId) {
    return reporteRepository.findByUsuarioId(usuarioId).stream()
        .map(ReporteDTO::fromDomain)
        .toList();
  }

  @Transactional(readOnly = true)
  public ReporteDTO obtenerReportePorId(String reporteId) {
    return reporteRepository.findById(reporteId)
        .map(ReporteDTO::fromDomain)
        .orElse(null);
  }
}
