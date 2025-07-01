
package Dominio.Usuarios;

import Dominio.Usuarios.Modelo.ID;
import Dominio.Usuarios.Modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, ID> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Spring Data generará la consulta automáticamente.
     * 
     * @param email El email a buscar.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    Optional<Usuario> findByEmail(String email);
}
