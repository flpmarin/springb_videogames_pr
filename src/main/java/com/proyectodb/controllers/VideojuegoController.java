package com.proyectodb.controllers;

import com.proyectodb.models.Videojuego;
import com.proyectodb.repositories.VideojuegoRepository;
import com.proyectodb.services.VideojuegoService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    @Autowired
    public VideojuegoRepository videojuegoRepository;

    @Autowired
    public VideojuegoService videojuegoService;

    @CrossOrigin
    @GetMapping
    public List<Videojuego> getAllVideojuegos() {
        return videojuegoRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuegoById(@PathVariable Long id) {
        Optional<Videojuego> videojuego = videojuegoRepository.findById(id);
        return videojuego.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Videojuego> createVideojuego(@RequestBody Videojuego videojuego) {
        Videojuego savedVideojuego = videojuegoRepository.save(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideojuego);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        if (!videojuegoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        videojuegoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> updateVideojuego(@PathVariable Long id, @RequestBody Videojuego updatedVideojuego) {
        if (!videojuegoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedVideojuego.setId(id);
        Videojuego savedVideojuego = videojuegoRepository.save(updatedVideojuego);
        return ResponseEntity.ok(savedVideojuego);
    }

    // Método para poblar la base de datos con los datos de una API de videojuegos
    @PostConstruct
    public void populateDatabase() {
        videojuegoService.fetchAndSaveVideojuegos();
    }
}
