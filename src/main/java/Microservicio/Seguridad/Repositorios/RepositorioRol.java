package Microservicio.Seguridad.Repositorios;

import Microservicio.Seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioRol extends MongoRepository <Rol, String> {
}
