package com.hidrored.presentacion.usuarios;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrarUsuarioRequestTest {

  @Test
  @DisplayName("Debe tener Getters y Setters funcionales para el registro")
  void testDtoLombokGettersAndSetters() {
    RegistrarUsuarioRequest request = new RegistrarUsuarioRequest();
    String nombreEsperado = "Usuario Prueba";
    String emailEsperado = "prueba@hidrored.com";
    String telefonoEsperado = "987654321";
    String passEsperado = "Secret123!";

    request.setNombre(nombreEsperado);
    request.setEmail(emailEsperado);
    request.setTelefono(telefonoEsperado);
    request.setPassword(passEsperado);

    assertNotNull(request);

    assertEquals(nombreEsperado, request.getNombre(), "El nombre no coincide");
    assertEquals(emailEsperado, request.getEmail(), "El email no coincide");
    assertEquals(telefonoEsperado, request.getTelefono(), "El teléfono no coincide");
    assertEquals(passEsperado, request.getPassword(), "La contraseña no coincide");
  }
}
