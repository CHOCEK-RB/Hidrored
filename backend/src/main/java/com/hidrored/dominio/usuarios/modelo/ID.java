package com.hidrored.dominio.usuarios.modelo;

import java.util.Objects;
import java.util.UUID; // Importa UUID

public class ID {

    private UUID value;

    // Constructor privado para forzar la creación a través de métodos estáticos
    private ID(UUID value) {
        // Validar que el UUID no sea nulo
        if (value == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        this.value = value;
    }

    // Constructor público para usar con el String ID desde la capa de aplicación/infraestructura
    public ID(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID en formato String no puede ser nulo o vacío.");
        }
        try {
            this.value = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato de ID inválido: " + value, e);
        }
    }

    // Método estático de fábrica para generar un nuevo ID
    public static ID generarNuevo() {
        return new ID(UUID.randomUUID());
    }

    // Método estático de fábrica para crear un ID desde un String
    public static ID de(String value) {
        return new ID(value);
    }

    // Getter para acceder al valor UUID
    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ID id1 = (ID) o;
        return Objects.equals(value, id1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}