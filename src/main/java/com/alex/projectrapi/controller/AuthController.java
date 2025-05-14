package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByUsernameAndPwd(loginRequest.getUsername(), loginRequest.getPassword());

        System.out.println("USUARIO: " + usuario + " - " + loginRequest.getUsername() + " - " + loginRequest.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // Clase interna para manejar la solicitud de login
    private static class LoginRequest {
        private String username;
        private String password;

        // Getters y setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}