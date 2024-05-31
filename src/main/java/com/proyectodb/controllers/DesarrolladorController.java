/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectodb.controllers;

import com.proyectodb.models.Desarrollador;
import com.proyectodb.repositories.DesarrolladorRepository;
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
@RequestMapping("/api/desarrolladores")
public class DesarrolladorController {
    
    @Autowired
    public DesarrolladorRepository desarrolladorRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Desarrollador> getAllDesarrolladores(){
        return desarrolladorRepository.findAll();
    }
    
    @CrossOrigin
     @GetMapping("/{id}")
    public ResponseEntity<Desarrollador> getDesarrolladorById(@PathVariable Long id) {
        Optional<Desarrollador> desarrolador = desarrolladorRepository.findById(id);
        return desarrolador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Desarrollador> createDesarrollador(@RequestBody Desarrollador desarrollador){
       Desarrollador savedDesarrollador = desarrolladorRepository.save(desarrollador);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedDesarrollador);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesarrollador(@PathVariable Long id) {
        if(!desarrolladorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        desarrolladorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
     @PutMapping("/{id}")
    public ResponseEntity<Desarrollador> updateDesarrollador(@PathVariable Long id, @RequestBody Desarrollador updatedDesarrollador) {
        if(!desarrolladorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedDesarrollador.setId(id);
       Desarrollador savedDesarrollador = desarrolladorRepository.save(updatedDesarrollador);
       return ResponseEntity.ok(savedDesarrollador);
    }
}
    
