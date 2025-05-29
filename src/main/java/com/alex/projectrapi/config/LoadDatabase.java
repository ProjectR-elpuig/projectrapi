package com.alex.projectrapi.config;

import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Productos de prueba

            // Usuarios de prueba con contraseñas encriptadas
//            usuarioRepository.save(new Usuario("ID123", "usuario1", passwordEncoder.encode("clave123"), "5551234", null));
//            usuarioRepository.save(new Usuario("ID456", "usuario2", passwordEncoder.encode("clave456"), "5555678", null));
        };
    }
}