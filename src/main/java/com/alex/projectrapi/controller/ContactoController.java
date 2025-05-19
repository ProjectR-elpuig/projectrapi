package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contacts")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los contactos
    @GetMapping
    public List<Contacto> getAllContacts() {
        return contactoRepository.findAll();
    }

    // Obtener contactos por citizenid del usuario
    @GetMapping("/citizen/{citizenid}")
    public ResponseEntity<List<Contacto>> getContactsByCitizenId(@PathVariable String citizenid) {
        List<Contacto> contactos = contactoRepository.findByUsuarioCitizenId(citizenid);

        if (contactos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Ordenamos alfabeticamente
        Collections.sort(contactos);

        return ResponseEntity.ok(contactos);
    }

    // Obtener contacto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactById(@PathVariable Integer id) {
        return contactoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo contacto
    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactoRequest request) {
        return usuarioRepository.findById(request.getCitizenId())
                .flatMap(usuario -> usuarioRepository.findByPhoneNumber(request.getPhoneNumber())
                        .map(contactoUsuario -> {
                            Contacto contacto = new Contacto();
                            contacto.setUsuario(usuario);
                            contacto.setContacto(contactoUsuario);
                            contacto.setName(request.getName());
                            return ResponseEntity.ok(contactoRepository.save(contacto));
                        }))
                .orElse(null);
    }

    // Eliminar contacto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Integer id) {
        return contactoRepository.findById(id)
                .map(contacto -> {
                    contactoRepository.delete(contacto);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Clase DTO para solicitudes
    private static class ContactoRequest {
        private String citizenId;
        private String phoneNumber;
        private String name;

        // Getters y Setters
        public String getCitizenId() { return citizenId; }
        public void setCitizenId(String citizenId) { this.citizenId = citizenId; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}