package Microservicio.Seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import Microservicio.Seguridad.Modelos.Rol;
import Microservicio.Seguridad.Modelos.Permiso;
import Microservicio.Seguridad.Modelos.PermisoRol;
import Microservicio.Seguridad.Repositorios.RepositorioRol;
import Microservicio.Seguridad.Repositorios.RepositorioPermiso;
import Microservicio.Seguridad.Repositorios.RepositorioPermisoRol;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping ("/permisos-roles")

public class ControladorPermisoRol {
    @Autowired
    private RepositorioPermisoRol myRepositorioPermisoRol;

    @Autowired
    private RepositorioRol myRepositorioRol;

    @Autowired
    private RepositorioPermiso myRepositorioPermiso;

    @GetMapping("")
    public List <PermisoRol> mostrarPermisosRoles() {
        return this.myRepositorioPermisoRol.findAll();
    }
    @GetMapping("{id}")
    public PermisoRol mostrarPermisoRol (@PathVariable String id) {
        PermisoRol permisoRol1 = this.myRepositorioPermisoRol.findById(id).orElse(null);
        return permisoRol1;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol crear (@PathVariable String id_rol, @PathVariable String id_permiso) {
        PermisoRol permisoRol1 = new PermisoRol();
        Rol rol1 = this.myRepositorioRol.findById(id_rol).get();
        Permiso permiso1 = this.myRepositorioPermiso.findById(id_permiso).get();
        if (rol1 != null && permiso1 != null) {
            permisoRol1.setPermiso(permiso1);
            permisoRol1.setRol(rol1);
            return this.myRepositorioPermisoRol.save(permisoRol1);
        }
        else {
            return null;
        }
    }
    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol actualizar (@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso) {
        PermisoRol permisoRol1 = this.myRepositorioPermisoRol.findById(id).orElse(null);
        Rol rol1 = this.myRepositorioRol.findById(id).orElse(null);
        Permiso permiso1 = this.myRepositorioPermiso.findById(id).orElse(null);
        if (permisoRol1 != null && rol1 != null && permiso1 != null) {
            permisoRol1.setRol(rol1);
            permisoRol1.setPermiso(permiso1);
            return this.myRepositorioPermisoRol.save(permisoRol1);
        }
        else {
            return null;
        }
    }
    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping ("{id}")
    public void eliminar (@PathVariable String id) {
        PermisoRol permisoRol1 = this.myRepositorioPermisoRol.findById(id).orElse(null);
        if (permisoRol1 != null) {
            this.myRepositorioPermisoRol.delete(permisoRol1);
        }
    }
    @GetMapping ("/validar-permiso/rol/{id_rol}")
    public PermisoRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso) {
        Permiso permiso1 = this.myRepositorioPermiso.getPermiso(infoPermiso.getUrl(), infoPermiso.getMetodo());
        Rol rol1 = this.myRepositorioRol.findById(id_rol).orElse(null);
        if (permiso1 != null && rol1 != null) {
            return this.myRepositorioPermisoRol.getPermisoRol(rol1.get_id(), permiso1.get_id());
        }
        else {
            return null;
        }
    }
}
