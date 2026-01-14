package com.hidrored.modulos.usuarios.dominio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "usuarios")
@Getter
public class Usuario {

  @Id
  private String id;

  private String nombre;

  @Indexed(unique = true)
  private String email;

  private String telefono;

  private String password;

  public Usuario(String nombre, String email, String telefono, String password) {
    this.id = UUID.randomUUID().toString();
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.password = password;
  }

  @SuppressWarnings("unused")
  private Usuario() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Usuario usuario = (Usuario) o;
    return Objects.equals(id, usuario.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
