package com.alex.projectrapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "citizenid")
    private String citizenId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String pwd;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] img; // Nuevo campo
}