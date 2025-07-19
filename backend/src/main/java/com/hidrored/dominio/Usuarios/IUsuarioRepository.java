package com.hidrored.dominio.Usuarios;

import com.hidrored.dominio.Usuarios.Modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {

  Optional<Usuario> findByEmail(String email);
}
