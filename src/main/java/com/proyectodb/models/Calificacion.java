package com.proyectodb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table (name="calificaciones")
public class Calificacion{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_Videojuego;
    private Long id_Usuario;
    private String calificacion;

    public Calificacion() {
    }

    public Calificacion(Long id, Long id_Videojuego, Long id_Usuario, String calificacion) {
        this.id = id;
        this.id_Videojuego = id_Videojuego;
        this.id_Usuario = id_Usuario;
        this.calificacion = calificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_Videojuego() {
        return id_Videojuego;
    }

    public void setId_Videojuego(Long id_Videojuego) {
        this.id_Videojuego = id_Videojuego;
    }

    public Long getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(Long id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

 

    
    
}