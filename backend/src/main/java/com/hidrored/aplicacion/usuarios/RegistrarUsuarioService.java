package com.hidrored.aplicacion.usuarios;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.Usuario;

@Service
public class RegistrarUsuarioService {
    private final IUsuarioRepository repository;
    private final PasswordEncoder encoder;

    public RegistrarUsuarioService(IUsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public UsuarioDTO ejecutar(RegistrarUsuarioCommand command) {
        validarEmailUnico(command.getEmail()); // Extract Method aplicado [cite: 86]
        String hashedPassword = encoder.encode(command.getPassword());
        Usuario nuevoUsuario = new Usuario(command.getNombre(), command.getEmail(), command.getTelefono(), hashedPassword);
        return UsuarioDTO.fromDomain(repository.save(nuevoUsuario));
    }

    private void validarEmailUnico(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }
    }
}