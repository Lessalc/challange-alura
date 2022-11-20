package com.lessalc.aluraflix.repositories;

import com.lessalc.aluraflix.entities.validacao.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String username);
}
