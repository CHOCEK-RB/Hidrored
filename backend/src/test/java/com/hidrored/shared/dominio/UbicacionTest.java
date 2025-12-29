package com.hidrored.shared.dominio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UbicacionTest {

  @Test
  void deberiaCrearUbicacionValida() {
    Ubicacion ubicacion = new Ubicacion(-12.0464, -77.0428, "Lima, Peru");
    assertEquals(-12.0464, ubicacion.getLatitud());
    assertEquals(-77.0428, ubicacion.getLongitud());
    assertEquals("Lima, Peru", ubicacion.getDireccion());
  }  
  @Test
  void deberiaLanzarExcepcionSiDatosSonNulos() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Ubicacion(null, -77.0428, "Direccion");
    });  
    assertThrows(IllegalArgumentException.class, () -> {
      new Ubicacion(-12.0, -77.0, "");
    });
  }
}
