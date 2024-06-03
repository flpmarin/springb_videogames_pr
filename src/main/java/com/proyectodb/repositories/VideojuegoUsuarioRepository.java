package com.proyectodb.repositories;

import com.proyectodb.models.Usuario;
import com.proyectodb.models.VideojuegoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideojuegoUsuarioRepository extends JpaRepository<VideojuegoUsuario, Long> {
    List<VideojuegoUsuario> findByUsuario(Usuario usuario); // Encuentra todos los videojuegos de un usuario
    void deleteByUsuarioIdAndVideojuegoId(Long usuarioId, Long videojuegoId);
    boolean existsByUsuarioIdAndVideojuegoId(Long usuarioId, Long videojuegoId);
}