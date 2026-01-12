package com.hidrored.modulos.usuarios.aplicacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hidrored.modulos.usuarios.dominio.IUsuarioRepository;
import com.hidrored.modulos.usuarios.dominio.Usuario;
import com.hidrored.modulos.usuarios.infraestructura.web.UsuarioDTO;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

  @Mock
  private IUsuarioRepository usuarioRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  private UsuarioApplicationService service;

  @BeforeEach
  void setUp() {
    service = new UsuarioApplicationService(usuarioRepository, passwordEncoder);
  }

  @Test
  void registrarUsuario_success() {
    RegistrarUsuarioCommand command = new RegistrarUsuarioCommand("Ana", "ana@example.com", "999999999", "secret");

    when(usuarioRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(command.getPassword())).thenReturn("hashed");
    Usuario saved = new Usuario(command.getNombre(), command.getEmail(), command.getTelefono(), "hashed");
    when(usuarioRepository.save(any(Usuario.class))).thenReturn(saved);

    UsuarioDTO dto = service.registrarUsuario(command);

    assertNotNull(dto);
    assertEquals("Ana", dto.getNombre());
    assertEquals("ana@example.com", dto.getEmail());
    assertEquals("999999999", dto.getTelefono());
  }

  @Test
  void registrarUsuario_duplicateEmail_throws() {
    RegistrarUsuarioCommand command = new RegistrarUsuarioCommand("Ana", "ana@example.com", "999999999", "secret");

    when(usuarioRepository.findByEmail(command.getEmail()))
        .thenReturn(Optional.of(new Usuario("X", command.getEmail(), "0", "h")));

    assertThrows(IllegalStateException.class, () -> service.registrarUsuario(command));
  }

  @Test
  void autenticarUsuario_success() {
    String email = "pepe@example.com";
    String raw = "pass";
    Usuario usuario = new Usuario("Pepe", email, "123", "hashed");

    when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(raw, "hashed")).thenReturn(true);

    UsuarioDTO dto = service.autenticarUsuario(email, raw);

    assertNotNull(dto);
    assertEquals("Pepe", dto.getNombre());
    assertEquals(email, dto.getEmail());
  }

  @Test
  void autenticarUsuario_invalidPassword_throws() {
    String email = "pepe@example.com";
    String raw = "wrong";
    Usuario usuario = new Usuario("Pepe", email, "123", "hashed");

    when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(raw, "hashed")).thenReturn(false);

    assertThrows(SecurityException.class, () -> service.autenticarUsuario(email, raw));
  }
}
