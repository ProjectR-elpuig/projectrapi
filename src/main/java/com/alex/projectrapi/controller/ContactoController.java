package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.ContactoChat;
import com.alex.projectrapi.model.Message;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.MessageRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contacts")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MessageRepository messageRepository;

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
            return ResponseEntity.ok(contactos);
        }
        // Ordenamos alfabeticamente
        Collections.sort(contactos);

        return ResponseEntity.ok(contactos);
    }

    // Obtener contacto por citizenid del usuario y numero
    @GetMapping("/citizen/{citizenid}/{phoneNumber}")
    public ResponseEntity<Optional<Contacto>> getContactByPhoneNumberAndCitizenId(@PathVariable String citizenid, @PathVariable String phoneNumber) {
        Optional<Contacto> contacto = contactoRepository.findByUsuarioCitizenIdAndContactoPhoneNumber(citizenid, phoneNumber);

        if (contacto != null && contacto.isPresent()) {
            return ResponseEntity.ok(contacto);
        }

        return ResponseEntity.ok(contacto);
    }

    // Obtener contacto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactById(@PathVariable Integer id) {
        return contactoRepository.findByIdWithUsuario(id) // Usar la consulta que carga relaciones
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuario por phoneNumber si existe
    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> getUsuarioByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByPhoneNumber(phoneNumber);
        Map<String, Object> response = new HashMap<>();
        response.put("found", usuarioOpt.isPresent());
        response.put("usuario", usuarioOpt.orElse(null));
        return ResponseEntity.ok(response);
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

    // Actualizar un contacto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(
            @PathVariable Integer id,
            @RequestBody ContactoRequest request) {

        return contactoRepository.findById(id)
                .map(contactoExistente -> {
                    // Buscar el usuario asociado al nuevo citizenId
                    Optional<Usuario> nuevoUsuario = usuarioRepository.findById(request.getCitizenId());
                    // Buscar el contacto por el nuevo número de teléfono
                    Optional<Usuario> nuevoContacto = usuarioRepository.findByPhoneNumber(request.getPhoneNumber());

                    if (nuevoUsuario.isPresent() && nuevoContacto.isPresent()) {
                        // Actualizar los campos
                        contactoExistente.setUsuario(nuevoUsuario.get());
                        contactoExistente.setContacto(nuevoContacto.get());
                        contactoExistente.setName(request.getName());

                        // Guardar cambios
                        Contacto contactoActualizado = contactoRepository.save(contactoExistente);
                        return ResponseEntity.ok(contactoActualizado);
                    } else {
                        return ResponseEntity.badRequest().body("Usuario o teléfono no encontrado");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar contacto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getCitizenId();


        // Usar findByIdWithUsuario en lugar de findById
        return contactoRepository.findByIdWithUsuario(id)
                .map(contacto -> {
                    // Verificar propiedad
                    if (!contacto.getUsuario().getCitizenId().equals(citizenId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos");
                    }
                    contactoRepository.delete(contacto);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<?> toggleBlockContact(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getCitizenId();

        return contactoRepository.findByIdWithUsuario(id)
                .map(contacto -> {
                    if (!contacto.getUsuario().getCitizenId().equals(citizenId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos");
                    }
                    contacto.setIsBlocked(!contacto.getIsBlocked()); // Invertir estado
                    return ResponseEntity.ok(contactoRepository.save(contacto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/chatting")
    public ResponseEntity<?> toggleChattingContact(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getCitizenId();

        return contactoRepository.findByIdWithUsuario(id)
                .map(contacto -> {
                    if (!contacto.getUsuario().getCitizenId().equals(citizenId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos");
                    }
                    contacto.setIsChatting(!contacto.getIsChatting());
                    return ResponseEntity.ok(contactoRepository.save(contacto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener contactos bloqueados
    @GetMapping("/blocked")
    public ResponseEntity<List<Contacto>> getBlockedContacts(@AuthenticationPrincipal UserDetails userDetails) {
        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getCitizenId();

        List<Contacto> contactos = contactoRepository.findBlockedContactsByCitizenId(citizenId);
        return ResponseEntity.ok(contactos);
    }

    // Endpoint para activar el chat
    @PutMapping("/{id}/start-chat")
    public ResponseEntity<?> startChat(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {

        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getCitizenId();

        return contactoRepository.findByIdWithUsuario(id)
                .map(contacto -> {
                    if (!contacto.getUsuario().getCitizenId().equals(citizenId)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos");
                    }
                    contacto.setIsChatting(true);
                    return ResponseEntity.ok(contactoRepository.save(contacto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener contactos en chat
    @GetMapping("/chats")
    public ResponseEntity<List<ContactoChat>> getChattingContacts(@AuthenticationPrincipal UserDetails userDetails) {
        String citizenId = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getCitizenId();

        List<Contacto> contactos = contactoRepository.findChattingContactsByCitizenId(citizenId);

        List<ContactoChat> dtos = contactos.stream().map(contacto -> {
            String userPhone = contacto.getUsuario().getPhoneNumber();
            String contactPhone = contacto.getContacto().getPhoneNumber();

            Message lastMsg = messageRepository.findLastMessageBetween(userPhone, contactPhone);

            return new ContactoChat(
                    contacto.getContactid(),
                    contacto.getName(),
                    contacto.getContacto().getPhoneNumber(),
                    contacto.getContacto().getImg(),
                    lastMsg != null ? lastMsg.getContent() : "No hay mensajes",
                    lastMsg != null ? lastMsg.getCreatedAt() : null
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Clase DTO para solicitudes
    private static class ContactoRequest {
        private String citizenId;
        private String phoneNumber;
        private String name;

        // Getters y Setters
        public String getCitizenId() {
            return citizenId;
        }

        public void setCitizenId(String citizenId) {
            this.citizenId = citizenId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}