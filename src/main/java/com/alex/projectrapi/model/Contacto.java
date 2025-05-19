package com.alex.projectrapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactid;

    @ManyToOne
    @JoinColumn(name = "citizenid", referencedColumnName = "citizenid")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "phone_number", referencedColumnName = "phone_number")
    private Usuario contacto;

    @Column(nullable = false)
    private String name;
}