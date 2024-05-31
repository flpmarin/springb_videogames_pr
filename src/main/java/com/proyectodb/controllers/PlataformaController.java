/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectodb.controllers;

import com.proyectodb.models.Plataforma;
import com.proyectodb.repositories.PlataformaRepository;
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

/**
 *
 * @author Felipe
 */
@RestController
@RequestMapping("/api/plataformas")
public class PlataformaController {
    
    @Autowired
    public PlataformaRepository plataformaRepository;
    
    
    
    @CrossOrigin //se usa porq el front se hará con Vue
    @GetMapping
    public List<Plataforma> getAllPlataformas(){
        return plataformaRepository.findAll();
    }
    
    
    @CrossOrigin
       @GetMapping("/{id}")
    public ResponseEntity<Plataforma> getPlataformaById(@PathVariable Long id) {
        Optional<Plataforma> plataforma = plataformaRepository.findById(id);
        return plataforma.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Plataforma> createPlataforma(@RequestBody Plataforma plataforma){
       Plataforma savedPlataforma = plataformaRepository.save(plataforma);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedPlataforma);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlataforma(@PathVariable Long id) {
        if(!plataformaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        plataformaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> updatePlataforma(@PathVariable Long id, @RequestBody Plataforma updatedPlataforma) {
        if(!plataformaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedPlataforma.setId(id);
       Plataforma savedPlataforma = plataformaRepository.save(updatedPlataforma);
       return ResponseEntity.ok(savedPlataforma);
    }
}

