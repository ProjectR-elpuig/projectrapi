package com.alex.projectrapi.config;

import com.alex.projectrapi.model.Producto;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ProductoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        return args -> {
            // Productos de prueba
            productoRepository.save(new Producto(null, "Laptop", "Laptop de última generación", 1200.99, 10));
            productoRepository.save(new Producto(null, "Teléfono", "Smartphone avanzado", 799.50, 25));
            productoRepository.save(new Producto(null, "Tablet", "Tablet de 10 pulgadas", 299.99, 15));

            // Usuarios de prueba
            usuarioRepository.save(new Usuario("ID123", "usuario1", "clave123", "5551234"));
            usuarioRepository.save(new Usuario("ID456", "usuario2", "clave456", "5555678"));
        };
    }
}