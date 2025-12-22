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
public class FileStorageService implements IStorageService {

    private final Path rootLocation;

    public FileStorageService(@Value("${storage.location:uploads}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("No se pudo inicializar el directorio de almacenamiento.", e);
        }
    }

    @Override
    public ImagenAdjunta store(MultipartFile file) {
        validarArchivo(file); // Extract Method aplicado
        
        String originalName = limpiarRuta(file.getOriginalFilename());
        String uniqueName = generarNombreUnico(originalName); // Extract Method aplicado

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
            return crearEntidadImagen(uniqueName, originalName, file);
        } catch (IOException e) {
            throw new StorageException("Error crítico al persistir el archivo: " + originalName, e);
        }
    }

    private void validarArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new StorageException("No se puede guardar un archivo vacío.");
        }
    }

    private String limpiarRuta(String filename) {
        return StringUtils.cleanPath(Objects.requireNonNull(filename));
    }

    private String generarNombreUnico(String originalFilename) {
        String extension = obtenerExtension(originalFilename); // Extract Method aplicado
        return UUID.randomUUID().toString() + extension;
    }

    private String obtenerExtension(String filename) {
        int lastDot = filename.lastIndexOf(".");
        return (lastDot == -1) ? "" : filename.substring(lastDot);
    }

    private ImagenAdjunta crearEntidadImagen(String uniqueName, String originalName, MultipartFile file) {
        return new ImagenAdjunta(
                uniqueName,
                originalName,
                file.getContentType(),
                file.getSize(),
                LocalDateTime.now()
        );
    }
}
