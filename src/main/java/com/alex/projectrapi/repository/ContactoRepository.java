package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    List<Contacto> findByUsuarioCitizenId(String citizenId);

    Optional<Contacto> findByUsuarioCitizenIdAndContactoPhoneNumber(String citizenId, String phoneNumber);

    @Query("SELECT c FROM Contacto c LEFT JOIN FETCH c.usuario WHERE c.contactid = :id")
    Optional<Contacto> findByIdWithUsuario(@Param("id") Integer id);


    @Query("SELECT c FROM Contacto c LEFT JOIN FETCH c.usuario u WHERE u.citizenId = :citizenId AND c.isBlocked = true")
    List<Contacto> findBlockedContactsByCitizenId(@Param("citizenId") String citizenId);

    @Query("SELECT c FROM Contacto c WHERE c.usuario.citizenId = :citizenId AND c.isChatting = true")
    List<Contacto> findChattingContactsByCitizenId(@Param("citizenId") String citizenId);

    boolean existsByUsuarioAndContacto(Usuario usuario, Usuario contacto);
}