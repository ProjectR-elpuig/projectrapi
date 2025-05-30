package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.ChatMessage;
import com.alex.projectrapi.model.Contacto;
import com.alex.projectrapi.model.MessageType;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.ContactoRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UsuarioRepository usuarioRepository;
    private final ContactoRepository contactoRepository;

    public ChatController(SimpMessageSendingOperations messagingTemplate, UsuarioRepository usuarioRepository, ContactoRepository contactoRepository) {
        this.messagingTemplate = messagingTemplate;
        this.usuarioRepository = usuarioRepository;
        this.contactoRepository = contactoRepository;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), chatMessage);
        if (chatMessage.getType() == MessageType.CHAT) {
            Optional<Usuario> sender = usuarioRepository.findByCitizenId(chatMessage.getSenderCitizenId());
            if (sender.isPresent()) {
                Optional<Contacto> receiver = contactoRepository.findByUsuarioCitizenIdAndContactoPhoneNumber(sender.get().getCitizenId(), chatMessage.getReceiverPhoneNumber());
                if (receiver.isPresent()) {
                    receiver = contactoRepository.findByUsuarioCitizenIdAndContactoPhoneNumber(receiver.get().getContacto().getCitizenId(), sender.get().getPhoneNumber());
                    if (receiver.isPresent() && !receiver.get().getIsBlocked()) {
                        messagingTemplate.convertAndSend("/topic/user." + chatMessage.getReceiverPhoneNumber(), chatMessage);
                    }
                }
            }
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Guardamos el usuario y el chatId en la sesión
        System.out.println("Sender y receiver: " + chatMessage.getSenderCitizenId() + " y " + chatMessage.getReceiverPhoneNumber() + "");
        headerAccessor.getSessionAttributes().put("chatId", chatMessage.getChatId());
        headerAccessor.getSessionAttributes().put("sender", chatMessage.getSenderCitizenId());
        headerAccessor.getSessionAttributes().put("receiver", chatMessage.getReceiverPhoneNumber());

        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), chatMessage);
    }

    @MessageMapping("/chat.disconnect")
    public void disconnectUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(((Usuario) headerAccessor.getSessionAttributes().get("sender")).getUsername() + " se ha desconectado del chat" + headerAccessor.getSessionAttributes().get("chatId"));

        // Notificar a los demás usuarios
        ChatMessage leaveMessage = new ChatMessage();
        leaveMessage.setType(MessageType.LEAVE);
        leaveMessage.setChatId(chatMessage.getChatId());
        leaveMessage.setSenderCitizenId(chatMessage.getSenderCitizenId());
        leaveMessage.setReceiverPhoneNumber(chatMessage.getReceiverPhoneNumber());

        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), leaveMessage);

        // Limpiar la sesión
        headerAccessor.getSessionAttributes().remove("chatId");
        headerAccessor.getSessionAttributes().remove("sender");
        headerAccessor.getSessionAttributes().remove("receiver");
    }
}
