package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Guardamos el usuario y el chatId en la sesión
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("chatId", chatMessage.getChatId());

        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), chatMessage);
    }
}
