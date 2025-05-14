package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsernameAndPwd(String username, String pwd);
    Usuario findByUsername(String username);
}