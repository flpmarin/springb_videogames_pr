package com.proyectodb.controllers;

import com.proyectodb.models.Usuario;
import com.proyectodb.repositories.UsuarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired // Inyección de dependencias, es decir, Spring se encarga de crear una instancia
               // de UsuarioRepository y la asigna a la variable usuarioRepository
    private UsuarioRepository usuarioRepository;

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @GetMapping("/search")
    public ResponseEntity<Usuario> getUsuarioByCorreoElectronico(@RequestParam String correoElectronico) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedUsuario.setId(id);
        Usuario savedUsuario = usuarioRepository.save(updatedUsuario);
        return ResponseEntity.ok(savedUsuario);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody Usuario usuario) { // el <?> es un comodín que representa
                                                                             // cualquier tipo de objeto
        Optional<Usuario> existingUsuarioCorreo = usuarioRepository
                .findByCorreoElectronico(usuario.getCorreoElectronico());
        Optional<Usuario> existingUsuarioNombre = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()); // Optional
                                                                                                                     // es
                                                                                                                     // una
                                                                                                                     // clase
                                                                                                                     // de
                                                                                                                     // Java
                                                                                                                     // que
                                                                                                                     // representa
                                                                                                                     // un
                                                                                                                     // valor
                                                                                                                     // que
                                                                                                                     // puede
                                                                                                                     // ser
                                                                                                                     // nulo
                                                                                                                     // o
                                                                                                                     // no
                                                                                                                     // nulo
                                                                                                                     // (existente
                                                                                                                     // o
                                                                                                                     // no
                                                                                                                     // existente)
                                                                                                                     // y
                                                                                                                     // se
                                                                                                                     // utiliza
                                                                                                                     // para
                                                                                                                     // evitar
                                                                                                                     // las
                                                                                                                     // excepciones
                                                                                                                     // de
                                                                                                                     // puntero
                                                                                                                     // nulo
                                                                                                                     // (NullPointerException),

        if (existingUsuarioCorreo.isPresent() || existingUsuarioNombre.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: User with email " + usuario.getCorreoElectronico() + " or username "
                            + usuario.getNombreUsuario() + " already exists!");
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @CrossOrigin(origins = "http://localhost:5173") // Ajusta esto según sea necesario
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario) {
        // Buscar el usuario por nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()); 

        // Verificar si el usuario existe
        if (usuarioOpt.isPresent()) {
            Usuario usuarioExistente = usuarioOpt.get();

            // Verificar si la contraseña coincide
            if (usuarioExistente.getContrasena().equals(usuario.getContrasena())) {
                // Si la contraseña coincide, devolver una respuesta exitosa
                return ResponseEntity.ok().body(usuarioExistente);
            }
        }

        // Si el usuario no existe o la contraseña no coincide, devolver una respuesta
        // de error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Login failed"));
    }

}
