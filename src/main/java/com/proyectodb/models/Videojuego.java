package com.proyectodb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="videojuegos")
public class Videojuego {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Long id_Plataforma;
    private Long id_Fecha_Lanzamiento;
    private Long id_Genero;
    private Long id_Desarrollador;
    private String descripcion;
    
    /*@Column (name="id_Genero")
    private Long Categoria;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId_Plataforma() {
        return id_Plataforma;
    }

    public void setId_Plataforma(Long id_Plataforma) {
        this.id_Plataforma = id_Plataforma;
    }

    public Long getId_Fecha_Lanzamiento() {
        return id_Fecha_Lanzamiento;
    }

    public void setId_Fecha_Lanzamiento(Long id_Fecha_Lanzamiento) {
        this.id_Fecha_Lanzamiento = id_Fecha_Lanzamiento;
    }

    public Long getId_Genero() {
        return id_Genero;
    }

    public void setId_Genero(Long id_Genero) {
        this.id_Genero = id_Genero;
    }

    public Long getId_Desarrollador() {
        return id_Desarrollador;
    }

    public void setId_Desarrollador(Long id_Desarrollador) {
        this.id_Desarrollador = id_Desarrollador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    // To String
    @Override
    public String toString() {
        return "Videojuego{" + "id=" + id + ", titulo=" + titulo + ", id_Plataforma=" + id_Plataforma +'}';
    }
    
    
    
   
    
    
}
