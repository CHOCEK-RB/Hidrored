package com.hidrored.aplicacion.reportes;

import com.hidrored.aplicacion.reportes.excepciones.StorageException;
import com.hidrored.dominio.reportes.modelo.ImagenAdjunta;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileStorageService {

  private final Path rootLocation = Paths.get("uploads");

  public FileStorageService() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("No se pudo inicializar el directorio de almacenamiento de archivos.", e);
    }
  }

  public ImagenAdjunta store(MultipartFile file) {
    if (file.isEmpty()) {
      throw new StorageException("No se puede guardar un archivo vacío.");
    }

    try {
      String originalFilename = file.getOriginalFilename();
      String extension = "";
      if (originalFilename != null && originalFilename.contains(".")) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      }
      String uniqueFilename = UUID.randomUUID().toString() + extension;

      Files.copy(file.getInputStream(), this.rootLocation.resolve(uniqueFilename));

      return new ImagenAdjunta(
          uniqueFilename,
          originalFilename,
          file.getContentType(),
          file.getSize(),
          LocalDateTime.now());
    } catch (IOException e) {
      throw new StorageException("Fallo al guardar el archivo.", e);
    }
  }
}
