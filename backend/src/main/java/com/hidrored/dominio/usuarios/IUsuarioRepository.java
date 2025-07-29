package com.hidrored.dominio.usuarios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hidrored.dominio.usuarios.modelo.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {

  Optional<Usuario> findByEmail(String email);
}
