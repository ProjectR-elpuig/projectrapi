package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.Message;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import com.alex.projectrapi.service.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final MessageService messageService;
    private final UsuarioRepository usuarioRepository;
    private final ContactoRepository contactoRepository;

    public ChatController(MessageService messageService, UsuarioRepository usuarioRepository, ContactoRepository contactoRepository) {
        this.messageService = messageService;
        this.usuarioRepository = usuarioRepository;
        this.contactoRepository = contactoRepository;
    }

    @MessageMapping("/send-message/{contactId}")
    @SendTo("/topic/messages/{contactId}")
    public Message handleMessage(
            @DestinationVariable Integer contactId,
            Message message,
            SimpMessageHeaderAccessor headerAccessor) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario sender = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Contacto contacto = contactoRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado"));

        // Validamos que el contacto realmente sea del usuario
        if(!contacto.getUsuario().getCitizenId().equals(sender.getCitizenId())) {
            throw new RuntimeException("Acceso no autorizado");
        }

        String receiverPhone = contacto.getContacto().getPhoneNumber();

        Message savedMessage = messageService.saveMessage(
                sender.getPhoneNumber(),
                receiverPhone,
                message.getContent()
        );

        return savedMessage;
    }
}
