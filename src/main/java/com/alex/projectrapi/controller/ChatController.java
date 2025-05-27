package com.alex.projectrapi.controller;

import com.alex.projectrapi.model.ChatMessage;
import com.alex.projectrapi.model.MessageType;
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

    @MessageMapping("/chat.disconnect")
    public void disconnectUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Disconnected from user: " + headerAccessor.getSessionAttributes().get("username"));
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();

        // Notificar a los demás usuarios
        ChatMessage leaveMessage = new ChatMessage();
        leaveMessage.setType(MessageType.LEAVE);
        leaveMessage.setSender(chatMessage.getSender());
        leaveMessage.setChatId(chatMessage.getChatId());

        messagingTemplate.convertAndSend("/topic/chat." + chatMessage.getChatId(), leaveMessage);

        // Limpiar la sesión
        headerAccessor.getSessionAttributes().remove("username");
        headerAccessor.getSessionAttributes().remove("chatId");
    }
}
