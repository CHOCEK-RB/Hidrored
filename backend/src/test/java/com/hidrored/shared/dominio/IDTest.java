package com.hidrored.shared.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class IDTest {

  @Test
  void deberiaCrearIDValidoDesdeString() {
    String uuidString = UUID.randomUUID().toString();
    ID id = new ID(uuidString);
    assertNotNull(id);
    assertEquals(uuidString, id.toString());
  }    
  @Test
  void deberiaGenerarNuevoIDCorrectamente() {
    ID id = ID.generarNuevo();
    assertNotNull(id);
    assertNotNull(id.getValue());
  }    
  @Test
  void deberiaLanzarExcepcionSiStringEsInvalido() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ID("id no valido");
    });
  }    
  @Test
  void deberiaLanzarExcepcionSiStringEsNulo() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ID(null);
    });
  }
}
