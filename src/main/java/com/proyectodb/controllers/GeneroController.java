package com.proyectodb.controllers;

import com.proyectodb.models.Genero;
import com.proyectodb.repositories.GeneroRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {
    
    @Autowired
    private GeneroRepository generoRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Genero> getAllGeneros() {
        return generoRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Genero> getGeneroById(@PathVariable Long id) {
        Optional<Genero> genero = generoRepository.findById(id);
        return genero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Genero> createGenero(@RequestBody Genero genero){
       Genero savedGenero = generoRepository.save(genero);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedGenero);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        if(!generoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        generoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Genero> updateGenero(@PathVariable Long id, @RequestBody Genero updatedGenero) {
        if(!generoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedGenero.setId(id);
       Genero savedGenero = generoRepository.save(updatedGenero);
       return ResponseEntity.ok(savedGenero);
    }
}