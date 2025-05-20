package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<Usuario> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return usuarioRepository.findByUsername(userDetails.getUsername())
                .map(ResponseEntity::ok)
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
}