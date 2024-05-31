
package com.proyectodb.repositories;

import com.proyectodb.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Felipe
 */
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    
}
