
package com.proyectodb.repositories;

import com.proyectodb.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Felipe
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
