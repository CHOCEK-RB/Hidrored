package com.hidrored.modulos.storage.aplicacion;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.hidrored.modulos.storage.dominio.IStorageService;
import com.hidrored.modulos.reportes.dominio.ImagenAdjunta;
import java.time.LocalDateTime;

@Service
public class GestionarArchivoService {

  private final IStorageService storageService;

  public GestionarArchivoService(IStorageService storageService) {
    this.storageService = storageService;
  }

  public ImagenAdjunta subirArchivo(MultipartFile file) {
    String url = storageService.guardar(file);
    return new ImagenAdjunta(
        url,
        file.getOriginalFilename(),
        file.getContentType(),
        file.getSize(),
        LocalDateTime.now());
  }

  public void eliminarArchivo(String nombreArchivo) {
    storageService.eliminar(nombreArchivo);
  }
}
