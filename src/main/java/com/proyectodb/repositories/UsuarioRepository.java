package com.proyectodb.repositories;

import com.proyectodb.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
