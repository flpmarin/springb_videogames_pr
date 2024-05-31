
package com.proyectodb.repositories;

import com.proyectodb.models.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 *
 * @author Felipe
 */
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNombre(String nombre);
}
