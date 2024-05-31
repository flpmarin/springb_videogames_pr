
package com.proyectodb.repositories;

import com.proyectodb.models.Fecha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Date;
/**
 *
 * @author Felipe
 */
public interface FechaRepository extends JpaRepository<Fecha, Long> {
    Optional<Fecha> findByFecha(Date fecha);
}

