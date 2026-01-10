package com.hidrored.modulos.usuarios.dominio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hidrored.modulos.usuarios.dominio.Usuario;

import java.util.Optional; 

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {

  Optional<Usuario> findByEmail(String email);
}
