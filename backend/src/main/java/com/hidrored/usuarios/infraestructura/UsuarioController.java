package com.hidrored.presentacion.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hidrored.aplicacion.usuarios.RegistrarUsuarioCommand;
import com.hidrored.aplicacion.usuarios.UsuarioApplicationService;
import com.hidrored.aplicacion.usuarios.UsuarioDTO;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioApplicationService usuarioService;

  public UsuarioController(UsuarioApplicationService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/registro")
  public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody RegistrarUsuarioRequest request) {
    RegistrarUsuarioCommand command = new RegistrarUsuarioCommand(
        request.getNombre(),
        request.getEmail(),
        request.getTelefono(),
        request.getPassword());
    UsuarioDTO usuarioCreado = usuarioService.registrarUsuario(command);
    return ResponseEntity.ok(usuarioCreado);
  }

  /**
   * Endpoint para el inicio de sesión de usuarios.
   * 
   * @param request Contiene el email y la contraseña.
   * @return Los datos del usuario si el login es exitoso.
   */
  @PostMapping("/login")
  public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequest request) {
    try {
      UsuarioDTO usuarioDTO = usuarioService.autenticarUsuario(request.getEmail(), request.getPassword());
      return ResponseEntity.ok(usuarioDTO);
    } catch (SecurityException e) {
      return ResponseEntity.status(401).build();
    }
  }
}
