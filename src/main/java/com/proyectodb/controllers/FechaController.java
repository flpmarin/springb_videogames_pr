/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectodb.controllers;

import com.proyectodb.models.Fecha;
import com.proyectodb.repositories.FechaRepository;
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
@RequestMapping("/api/fechas")
public class FechaController {
    
    @Autowired
    public FechaRepository fechaRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Fecha> getAllFechas(){
        return fechaRepository.findAll();
    }
    
    @CrossOrigin
     @GetMapping("/{id}")
    public ResponseEntity<Fecha> getFechaById(@PathVariable Long id) {
        Optional<Fecha> fecha = fechaRepository.findById(id);
        return fecha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Fecha> createFecha(@RequestBody Fecha fecha){
       Fecha savedFecha = fechaRepository.save(fecha);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedFecha);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFecha(@PathVariable Long id) {
        if(!fechaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fechaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Fecha> updateFecha(@PathVariable Long id, @RequestBody Fecha updatedFecha) {
        if(!fechaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedFecha.setId(id);
       Fecha savedFecha = fechaRepository.save(updatedFecha);
       return ResponseEntity.ok(savedFecha);
    }
    
    
}