package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    // Añade este método
    List<Contacto> findByUsuarioCitizenId(String citizenId);
}