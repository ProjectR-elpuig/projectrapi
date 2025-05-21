package com.alex.projectrapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contacto implements Comparable<Contacto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "citizenid", referencedColumnName = "citizenid")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "phone_number", referencedColumnName = "phone_number")
    private Usuario contacto;

    @Column(nullable = false)
    private String name;

    @Column(name = "isblocked", columnDefinition = "boolean default false")
    private Boolean isBlocked = false;

// Añadir getter y setter (si usas Lombok @Data, los genera automáticamente)

    @Override
    public int compareTo(Contacto o) {
        return this.name.compareToIgnoreCase(o.getName());
    }
}