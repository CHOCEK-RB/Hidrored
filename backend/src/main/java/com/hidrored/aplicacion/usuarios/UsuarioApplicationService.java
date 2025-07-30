package com.hidrored.aplicacion.usuarios;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.Usuario;

@Service
public class UsuarioApplicationService {

  private final IUsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public UsuarioApplicationService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public UsuarioDTO registrarUsuario(RegistrarUsuarioCommand command) {
    if (usuarioRepository.findByEmail(command.getEmail()).isPresent()) {
      throw new IllegalStateException("El correo electrónico ya está en uso.");
    }
    String hashedPassword = passwordEncoder.encode(command.getPassword());
    Usuario nuevoUsuario = new Usuario(
        command.getNombre(),
        command.getEmail(),
        command.getTelefono(),
        hashedPassword);
    Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
    return UsuarioDTO.fromDomain(usuarioGuardado);
  }

  /**
   * Caso de uso para autenticar un usuario.
   * 
   * @param email    El email del usuario.
   * @param password La contraseña en texto plano.
   * @return El DTO del usuario si las credenciales son correctas.
   * @throws SecurityException si las credenciales son incorrectas.
   */
  @Transactional(readOnly = true)
  public UsuarioDTO autenticarUsuario(String email, String password) {
    Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new SecurityException("Credenciales inválidas"));

    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      throw new SecurityException("Credenciales inválidas");
    }

    return UsuarioDTO.fromDomain(usuario);
  }
}
