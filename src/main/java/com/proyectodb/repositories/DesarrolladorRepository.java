
package com.proyectodb.repositories;

import com.proyectodb.models.Desarrollador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author Felipe
 */
public interface DesarrolladorRepository extends JpaRepository<Desarrollador, Long> {
    Optional<Desarrollador> findByNombre(String nombre);
}

