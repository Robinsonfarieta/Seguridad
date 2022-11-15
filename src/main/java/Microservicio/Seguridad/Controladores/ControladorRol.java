package Microservicio.Seguridad.Controladores;

import Microservicio.Seguridad.Modelos.Rol;
import Microservicio.Seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roles")

public class ControladorRol{
    @Autowired
    private RepositorioRol myRepositorioRol;

    @GetMapping("")
    public List <Rol> mostrarRoles (){
        return this.myRepositorioRol.findAll();
    }
    @GetMapping("{id}")
    public Rol mostrarRol (@PathVariable String id) {
        Rol rol1 = this.myRepositorioRol.findById(id).orElse(null);
        return rol1;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol crear (@RequestBody Rol infoRol) {
        return this.myRepositorioRol.save(infoRol);
    }
    @PutMapping("{id}")
    public Rol actualizar (@PathVariable String id, @RequestBody Rol infoRol) {
        Rol rol1 = this.myRepositorioRol.findById(id).orElse(null);
        if (rol1 != null) {
            rol1.setNombre(infoRol.getNombre());
            rol1.setDescripcion(infoRol.getDescripcion());
            return this.myRepositorioRol.save(rol1);
        }
        else {
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar (@PathVariable String id) {
        this.myRepositorioRol.deleteById(id);
    }
}
