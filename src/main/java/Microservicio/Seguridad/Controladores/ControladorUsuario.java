package Microservicio.Seguridad.Controladores;
import Microservicio.Seguridad.Modelos.Usuario;
import Microservicio.Seguridad.Modelos.Rol;
import Microservicio.Seguridad.Repositorios.RepositorioUsuario;
import Microservicio.Seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")

public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario myRepositoriousuario;

    @Autowired
    private RepositorioRol myRepositoriorol;

    @GetMapping(" ")
    public List <Usuario> mostrarUsuarios() {
        return this.myRepositoriousuario.findAll();
    }

    @GetMapping("{id}")
    public Usuario mostrarUsuario (@PathVariable String id) {
        Usuario usuario1 = this.myRepositoriousuario.findById(id).orElse(null);
        return usuario1;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario crear (@RequestBody Usuario infoUsuario) {
        String contrasena = infoUsuario.getContrasena();
        infoUsuario.setContrasena(convertirSHA256(contrasena));
        return this.myRepositoriousuario.save(infoUsuario);
    }

    @PutMapping("{id}")
    public Usuario actualizar (@PathVariable String id, @RequestBody Usuario infoUsuario){
        Usuario usuario1 = this.myRepositoriousuario.findById(id).orElse(null);
        if (usuario1 != null){
            usuario1.setSeudonimo(infoUsuario.getSeudonimo());
            usuario1.setCorreo(infoUsuario.getCorreo());
            usuario1.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return this.myRepositoriousuario.save(usuario1);
        }
        else {
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable String id) {
        Usuario usuario1 = this.myRepositoriousuario.findById(id).orElse(null);
        if (usuario1 != null){
            this.myRepositoriousuario.delete(usuario1);
        }
    }
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRol(@PathVariable String id, @PathVariable String id_rol) {
        Usuario usuario1 = this.myRepositoriousuario.findById(id).orElse(null);
        Rol rol1 = this.myRepositoriorol.findById(id_rol).orElse(null);
        if (usuario1 != null && rol1 != null) {
            usuario1.setRol(rol1);
            return this.myRepositoriousuario.save(usuario1);
        } else {
            return null;
        }
    }
    @PostMapping("/login")
    public Usuario autenticacion(@RequestBody Usuario infoUsuario, final HttpServletResponse response)throws IOException {
        Usuario usuario1 = this.myRepositoriousuario.getUserByEmail(infoUsuario.getCorreo());
        String contrasena1 = convertirSHA256(infoUsuario.getContrasena());
        if (usuario1 != null && usuario1.getContrasena().equals(contrasena1)){
            usuario1.setContrasena("");
            return usuario1;
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    //@DeleteMapping("{id}")
    //public void eliminar(@PathVariable String id) {
    //    this.myRepositorioUsuario.deleteById(id);
    //}

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b: hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
