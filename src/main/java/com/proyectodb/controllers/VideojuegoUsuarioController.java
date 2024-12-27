package com.proyectodb.controllers;

import com.proyectodb.models.Usuario;
import com.proyectodb.models.Videojuego;
import com.proyectodb.models.VideojuegoUsuario;
import com.proyectodb.repositories.UsuarioRepository;
import com.proyectodb.repositories.VideojuegoRepository;
import com.proyectodb.repositories.VideojuegoUsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios_videojuegos")
public class VideojuegoUsuarioController {

    @Autowired
    private VideojuegoUsuarioRepository videojuegoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @CrossOrigin
    @GetMapping("/videojuegos/usuario/{nombreUsuario}")
    public ResponseEntity<List<VideojuegoUsuario>> getVideojuegosByNombreUsuario(@PathVariable String nombreUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<VideojuegoUsuario> videojuegos = videojuegoUsuarioRepository.findByUsuario(usuario.get());
        if (videojuegos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videojuegos);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> addVideojuegoToUsuario(@RequestParam Long usuarioId, @RequestParam Long videojuegoId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(videojuegoId);

        if (!usuarioOpt.isPresent() || !videojuegoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Verificar si la relación ya existe
        if (videojuegoUsuarioRepository.existsByUsuarioIdAndVideojuegoId(usuarioId, videojuegoId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La relación ya existe");
        }

        VideojuegoUsuario videojuegoUsuario = new VideojuegoUsuario();
        videojuegoUsuario.setUsuario(usuarioOpt.get());
        videojuegoUsuario.setVideojuego(videojuegoOpt.get());
        VideojuegoUsuario savedVideojuegoUsuario = videojuegoUsuarioRepository.save(videojuegoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideojuegoUsuario);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> removeVideojuegoFromUsuario(@RequestParam Long usuarioId,
            @RequestParam Long videojuegoId) {
        if (!videojuegoUsuarioRepository.existsByUsuarioIdAndVideojuegoId(usuarioId, videojuegoId)) {
            return ResponseEntity.notFound().build();
        }
        videojuegoUsuarioRepository.deleteByUsuarioIdAndVideojuegoId(usuarioId, videojuegoId);
        return ResponseEntity.noContent().build();
    }
}