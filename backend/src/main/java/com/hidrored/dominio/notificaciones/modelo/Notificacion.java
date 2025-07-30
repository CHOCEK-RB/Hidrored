package com.hidrored.dominio.notificaciones.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hidrored.dominio.usuarios.modelo.ID;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "notificaciones")
public class Notificacion {

    @Id
    private ID id;

    private ID usuarioId; // A qué usuario pertenece la notificación
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private boolean leida;

    /**
     * Constructor para crear una nueva notificación.
     * @param usuarioId El ID del usuario que recibirá la notificación.
     * @param mensaje El contenido del mensaje.
     */
    public Notificacion(ID usuarioId, String mensaje) {
        if (usuarioId == null || mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuarioId y el mensaje son obligatorios.");
        }
        this.id = ID.generarNuevo();
        this.usuarioId = usuarioId;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false; // Las notificaciones nuevas no están leídas por defecto
    }

    /**
     * Constructor vacío para uso de Spring Data.
     */
    private Notificacion() {}

    // --- Métodos de Comportamiento ---

    public void marcarComoLeida() {
        this.leida = true;
    }

    // --- Getters ---

    public ID getId() {
        return id;
    }



    public ID getUsuarioId() {
        return usuarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean isLeida() {
        return leida;
    }

    // --- equals y hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacion that = (Notificacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
