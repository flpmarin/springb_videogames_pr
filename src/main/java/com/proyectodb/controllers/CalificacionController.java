/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectodb.controllers;

import com.proyectodb.models.Calificacion;
import com.proyectodb.repositories.CalificacionRepository;
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
@RequestMapping("/api/calificaciones")
public class CalificacionController {
    
    @Autowired
    public CalificacionRepository calificacionRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Calificacion> getAllCalificaciones(){
        return calificacionRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> getCalificacionById(@PathVariable Long id) {
        Optional<Calificacion> calificacion = calificacionRepository.findById(id);
        return calificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Calificacion> createCalificacion(@RequestBody Calificacion calificacion){
       Calificacion savedCalificacion = calificacionRepository.save(calificacion);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedCalificacion);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Long id) {
        if(!calificacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        calificacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> updateCalificacion(@PathVariable Long id, @RequestBody Calificacion updatedCalificacion) {
        if(!calificacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedCalificacion.setId(id);
       Calificacion savedCalificacion = calificacionRepository.save(updatedCalificacion);
       return ResponseEntity.ok(savedCalificacion);
    }
}

