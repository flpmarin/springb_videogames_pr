package com.proyectodb.repositories;

import com.proyectodb.models.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author Felipe
 */
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
    Optional<Videojuego> findByTitulo(String titulo);
}
