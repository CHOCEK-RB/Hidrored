package com.hidrored.aplicacion.usuarios;

import com.hidrored.dominio.usuarios.modelo.Usuario;
import lombok.Getter;

/**
 * Objeto de Transferencia de Datos (DTO) para exponer información del usuario.
 * Solo contiene información necesaria para las respuestas hacia el cliente.
 */
@Getter
public class UsuarioDTO {

    private final String id;
    private final String nombre;
    private final String email;
    private final String telefono;

    /**
     * Constructor privado para asegurar la creación controlada vía método factory.
     *
     * @param id       Identificador del usuario.
     * @param nombre   Nombre del usuario.
     * @param email    Correo electrónico del usuario.
     * @param telefono Teléfono del usuario.
     */
    private UsuarioDTO(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Método de fábrica para crear un DTO desde una entidad de dominio Usuario.
     * 
     * @param usuario La entidad de dominio Usuario.
     * @return Una instancia de UsuarioDTO con los datos necesarios.
     */
    public static UsuarioDTO desdeDominio(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono()
        );
    }
}
