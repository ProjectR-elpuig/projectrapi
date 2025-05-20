package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<Usuario> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    // Asegurar que los campos necesarios están presentes
                    return ResponseEntity.ok()
                            .body(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ProfileController.java
    @PutMapping
    public ResponseEntity<Usuario> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Usuario updatedUser) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    return ResponseEntity.ok(usuarioRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/image")
    public ResponseEntity<Usuario> uploadImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile file) throws IOException {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    try {
                        user.setImg(file.getBytes());
                        return ResponseEntity.ok(usuarioRepository.save(user));
                    } catch (IOException e) {
                        throw new RuntimeException("Error al procesar la imagen", e);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/image")
    public ResponseEntity<Usuario> deleteImage(@AuthenticationPrincipal UserDetails userDetails) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    user.setImg(null);
                    return ResponseEntity.ok(usuarioRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangePasswordRequest request) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    // Verificar contraseña actual (comparando el hash)
                    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPwd())) {
                        return ResponseEntity.badRequest().body("Contraseña actual incorrecta");
                    }
                    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                        return ResponseEntity.badRequest().body("Las contraseñas no coinciden");
                    }
                    // Codificar la nueva contraseña
                    user.setPwd(passwordEncoder.encode(request.getNewPassword()));
                    usuarioRepository.save(user);
                    return ResponseEntity.ok("Contraseña cambiada con éxito");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Clase DTO para la solicitud
    private static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
        private String confirmPassword;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
}