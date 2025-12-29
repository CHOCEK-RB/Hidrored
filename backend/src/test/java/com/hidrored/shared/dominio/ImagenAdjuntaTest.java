package com.hidrored.shared.dominio;


import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ImagenAdjuntaTest {  
  @Test
  void deberiaCrearImagenAdjuntaValida() {
    String url = "http://ejemplo.com/foto.jpg";
    String nombre = "foto.jpg";
    String mime = "image/jpeg";
    Long tamano = 1024L;

    LocalDateTime fecha = LocalDateTime.now();  
    ImagenAdjunta imagen = new ImagenAdjunta(url, nombre, mime, tamano, fecha);  

    assertNotNull(imagen);
    assertEquals(url, imagen.getUrl());
    assertEquals(nombre, imagen.getNombreArchivo());
    assertEquals(mime, imagen.getTipoMime());
    assertEquals(tamano, imagen.getTamanioBytes());
    assertEquals(fecha, imagen.getFechaSubida());
  }  
  @Test
  void deberiaLanzarExcepcionConDatosInvalidos() {
    LocalDateTime fecha = LocalDateTime.now();  
    assertThrows(IllegalArgumentException.class, () -> 
        new ImagenAdjunta(null, "foto.jpg", "image/png", 100L, fecha)
    );  
    assertThrows(IllegalArgumentException.class, () -> 
        new ImagenAdjunta("http://url", "", "image/png", 100L, fecha)
    );  
    assertThrows(IllegalArgumentException.class, () -> 
        new ImagenAdjunta("http://url", "foto.jpg", "image/png", 0L, fecha)
    );  
    assertThrows(IllegalArgumentException.class, () -> 
        new ImagenAdjunta("http://url", "foto.jpg", "image/png", 100L, null)
    );
  }
}
