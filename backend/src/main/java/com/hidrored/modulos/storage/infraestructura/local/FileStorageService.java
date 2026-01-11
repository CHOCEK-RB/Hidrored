package com.hidrored.modulos.storage.infraestructura.local;

import com.hidrored.modulos.storage.dominio.IStorageService;
import com.hidrored.modulos.storage.dominio.excepciones.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileStorageService implements IStorageService {

  private final Path rootLocation = Paths.get("uploads");

  public FileStorageService() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("No se pudo inicializar el directorio de almacenamiento de archivos.", e);
    }
  }

  @Override
  public String guardar(MultipartFile file) {
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

      return uniqueFilename; // Retorna la URL (nombre de archivo único)
    } catch (IOException e) {
      throw new StorageException("Fallo al guardar el archivo.", e);
    }
  }

  @Override
  public void eliminar(String url) {
    // Implementar lógica de eliminación si es necesaria
    // Por ahora, no se implementa nada específico
  }
}
