package com.hidrored.aplicacion.usuarios;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.Usuario;

@Service
public class AutenticarUsuarioService {
    private final IUsuarioRepository repository;
    private final PasswordEncoder encoder;

    public AutenticarUsuarioService(IUsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public UsuarioDTO ejecutar(String email, String password) {
        Usuario usuario = repository.findByEmail(email)
            .orElseThrow(() -> new SecurityException("Credenciales inválidas"));

        if (!encoder.matches(password, usuario.getPassword())) {
            throw new SecurityException("Credenciales inválidas");
        }
        return UsuarioDTO.fromDomain(usuario);
    }
}