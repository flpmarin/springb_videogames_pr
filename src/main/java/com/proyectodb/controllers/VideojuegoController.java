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

    @CrossOrigin // crossOrigin permite que la API sea consumida por un cliente en un dominio diferente
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuegoById(@PathVariable Long id) { // responseEntity es una clase de Spring que representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        Optional<Videojuego> videojuego = videojuegoRepository.findById(id); //videojuegoRepository es una interfaz que extiende de JpaRepository y proporciona métodos para interactuar con la base de datos, findById se ecuentra en JpaRepository
        return videojuego.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // map es un método de Optional que permite transformar el valor si está presente, orElseGet es un método de Optional que permite devolver un valor predeterminado si el valor no está presente, es decir, si no se encontró el videojuego con el ID especificado en la base de datos se devuelve un estado 404 (no encontrado) en la respuesta HTTP, es decir retorna un 404 si no se encuentra el videojuego con el ID especificado en la base de datos y si se encuentra retorna el videojuego con el ID especificado. ReponseEntity.ok es un método de ResponseEntity que crea una respuesta HTTP con el código de estado 200 (OK) y el cuerpo de la respuesta es el videojuego con el ID especificado
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Videojuego> createVideojuego(@RequestBody Videojuego videojuego) {
        Videojuego savedVideojuego = videojuegoRepository.save(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideojuego);// ResponseEntity.status(HttpStatus.CREATED) crea una respuesta HTTP con el código de estado 201 (creado) y el cuerpo de la respuesta es el videojuego guardado en la base de datos, es decir status es un método de ResponseEntity que permite establecer el código de estado de la respuesta HTTP, body es un método de ResponseEntity que permite establecer el cuerpo de la respuesta HTTP, httpStatus es una enumeración de Spring que representa los códigos de estado HTTP y CREATED es un código de estado HTTP que indica que la solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado
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

        updatedVideojuego.setId(id); // Asegurarse de que el ID del videojuego actualizado sea el mismo que el ID del videojuego que se quiere actualizar
        Videojuego savedVideojuego = videojuegoRepository.save(updatedVideojuego); // Guardar el videojuego actualizado en la base de datos
        return ResponseEntity.ok(savedVideojuego); // Responder con el videojuego actualizado
    }

    // Método para poblar la base de datos con los datos de una API de videojuegos
    @PostConstruct
    public void populateDatabase() {
        videojuegoService.fetchAndSaveVideojuegos();
    }
}
