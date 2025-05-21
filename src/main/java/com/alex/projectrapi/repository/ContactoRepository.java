package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    List<Contacto> findByUsuarioCitizenId(String citizenId);

    @Query("SELECT c FROM Contacto c LEFT JOIN FETCH c.usuario WHERE c.contactid = :id")
    Optional<Contacto> findByIdWithUsuario(@Param("id") Integer id);
}