package com.hidrored.aplicacion.reportes;

import tools.jackson.databind.JsonNode;

import com.hidrored.dominio.reportes.IReporteRepository;
import com.hidrored.dominio.reportes.modelo.*;
import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.shared.dominio.ImagenAdjunta;
import com.hidrored.shared.dominio.Ubicacion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReporteApplicationService {

  private final IReporteRepository reporteRepository;
  private final IUsuarioRepository usuarioRepository;
  private final FileStorageService fileStorageService;
  private final RestTemplate restTemplate;

  public ReporteApplicationService(IReporteRepository reporteRepository, IUsuarioRepository usuarioRepository,
      FileStorageService fileStorageService, RestTemplate restTemplate) {
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
      ImagenAdjunta imagenGuardada = fileStorageService.store(imagenFile);
      nuevoReporte.setImagenAdjunta(imagenGuardada);
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
