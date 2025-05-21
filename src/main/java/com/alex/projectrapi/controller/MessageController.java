package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.Message;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import com.alex.projectrapi.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UsuarioRepository usuarioRepository;
    private final ContactoRepository contactoRepository;

    public MessageController(MessageService messageService, UsuarioRepository usuarioRepository, ContactoRepository contactoRepository) {
        this.messageService = messageService;
        this.usuarioRepository = usuarioRepository;
        this.contactoRepository = contactoRepository;
    }

    @PostMapping("/send/{contactId}")
    public ResponseEntity<Message> sendMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer contactId,
            @RequestBody MessageRequest messageRequest) {

        // Obtener usuario actual
        Usuario sender = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener contacto y validar pertenencia
        Contacto contacto = contactoRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado"));

        if(!contacto.getUsuario().getCitizenId().equals(sender.getCitizenId())) {
            throw new RuntimeException("No tienes permiso para enviar a este contacto");
        }

        // Obtener teléfono del contacto
        String receiverPhone = contacto.getContacto().getPhoneNumber();

        Message message = messageService.saveMessage(
                sender.getPhoneNumber(),
                receiverPhone,
                messageRequest.getContent()
        );

        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation/{contactId}")
    public ResponseEntity<List<Message>> getConversation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer contactId) {

        Usuario currentUser = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar contacto
        Contacto contacto = contactoRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado"));

        if(!contacto.getUsuario().getCitizenId().equals(currentUser.getCitizenId())) {
            throw new RuntimeException("Acceso no autorizado a esta conversación");
        }

        List<Message> messages = messageService.getConversation(
                currentUser.getPhoneNumber(),
                contacto.getContacto().getPhoneNumber()
        );

        return ResponseEntity.ok(messages);
    }

    static class MessageRequest {
        private String receiverPhone;
        private String content;

        // Getters y setters
        public String getReceiverPhone() { return receiverPhone; }
        public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}
