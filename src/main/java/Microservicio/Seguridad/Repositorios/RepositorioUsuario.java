package Microservicio.Seguridad.Repositorios;

import Microservicio.Seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioUsuario extends MongoRepository <Usuario, String>{
    @Query("{'correo':?0}") // SELECT * FROM WHERE CORREO = ?
    public Usuario getUserByEmail(String correo);
}
