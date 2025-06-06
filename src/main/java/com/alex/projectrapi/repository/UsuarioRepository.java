package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByPhoneNumber(String phoneNumber);
    Optional<Usuario> findByCitizenId(String citizenId);
    boolean existsByPhoneNumber(String phoneNumber);
}