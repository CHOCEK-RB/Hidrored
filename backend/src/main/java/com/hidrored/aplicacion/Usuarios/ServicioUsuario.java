package com.hidrored.aplicacion.usuarios;

import com.hidrored.dominio.usuarios.IUsuarioRepository;
import com.hidrored.dominio.usuarios.modelo.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación encargado de la gestión de usuarios.
 * Se encarga de coordinar operaciones de negocio relacionadas a usuarios.
 */
@Service
public class ServicioUsuario {

    private final IUsuarioRepository repositorioUsuario;
    private final PasswordEncoder codificadorContrasenia;

    public ServicioUsuario(IUsuarioRepository repositorioUsuario, PasswordEncoder codificadorContrasenia) {
        this.repositorioUsuario = repositorioUsuario;
        this.codificadorContrasenia = codificadorContrasenia;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param comando Datos necesarios para el registro.
     * @return DTO del usuario registrado.
     * @throws CorreoDuplicadoException si el correo ya está en uso.
     */
    @Transactional
    public UsuarioDTO registrarNuevoUsuario(RegistrarUsuarioCommand comando) {
        verificarCorreoDisponible(comando.getEmail());

        String contraseniaHasheada = codificadorContrasenia.encode(comando.getPassword());

        Usuario usuario = new Usuario(
                comando.getNombre(),
                comando.getEmail(),
                comando.getTelefono(),
                contraseniaHasheada
        );

        Usuario usuarioGuardado = repositorioUsuario.save(usuario);

        return UsuarioDTO.fromDomain(usuarioGuardado);
    }

    /**
     * Autentica un usuario con sus credenciales.
     *
     * @param email    Correo electrónico del usuario.
     * @param password Contraseña en texto plano.
     * @return DTO del usuario autenticado.
     * @throws CredencialesInvalidasException si las credenciales son incorrectas.
     */
    @Transactional(readOnly = true)
    public UsuarioDTO autenticarUsuario(String email, String password) {
        Usuario usuario = repositorioUsuario.findByEmail(email)
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));

        if (!codificadorContrasenia.matches(password, usuario.getPassword())) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }

        return UsuarioDTO.fromDomain(usuario);
    }

    /**
     * Verifica si un correo electrónico ya está registrado.
     *
     * @param email Correo a verificar.
     * @throws CorreoDuplicadoException si el correo ya existe.
     */
    private void verificarCorreoDisponible(String email) {
        if (repositorioUsuario.findByEmail(email).isPresent()) {
            throw new CorreoDuplicadoException("El correo electrónico ya está en uso: " + email);
        }
    }
}
