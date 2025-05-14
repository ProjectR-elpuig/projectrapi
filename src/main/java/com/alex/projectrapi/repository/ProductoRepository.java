package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Puedes añadir métodos personalizados aquí si los necesitas
    // Ejemplo: List<Producto> findByNombreContaining(String nombre);
}
