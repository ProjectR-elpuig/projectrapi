package com.alex.projectrapi.config;

import com.alex.projectrapi.model.ChatMessage;
import com.alex.projectrapi.model.MessageType;
import com.alex.projectrapi.model.Usuario;
import com.alex.projectrapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UsuarioRepository usuarioRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String chatId = (String) headerAccessor.getSessionAttributes().get("chatId");
        String senderCitizenId = (String) headerAccessor.getSessionAttributes().get("sender");
        String receiverPhoneNumber = (String) headerAccessor.getSessionAttributes().get("receiver");

        if (senderCitizenId != null && receiverPhoneNumber != null && chatId != null) {
            log.info("user disconnected: {}", senderCitizenId);
            Usuario sender = usuarioRepository.findByCitizenId(senderCitizenId).get();
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .chatId(chatId)
                    .senderCitizenId(senderCitizenId)
                    .receiverPhoneNumber(receiverPhoneNumber)
                    .build();
            messagingTemplate.convertAndSend("/topic/chat." + chatId, chatMessage);
        }
    }

}
