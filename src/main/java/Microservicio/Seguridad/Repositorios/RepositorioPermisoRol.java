package Microservicio.Seguridad.Repositorios;

import Microservicio.Seguridad.Modelos.PermisoRol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioPermisoRol extends MongoRepository <PermisoRol, String> {
    @Query("{'rol.$id': ObjectId(?0), 'permiso.$id': ObjectId(?1)}") //SELECT * FROM PERMISOROL WHERE ID_ROL(JOIN) AND ID_PERMISO(JOIN)
    PermisoRol getPermisoRol(String id_rol, String id_permiso);
}
