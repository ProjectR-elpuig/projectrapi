package com.alex.projectrapi.service;

import com.alex.projectrapi.model.Message;
import com.alex.projectrapi.repository.MessageRepository;
import com.alex.projectrapi.repository.UsuarioRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final UsuarioRepository usuarioRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate, UsuarioRepository usuarioRepository) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.usuarioRepository = usuarioRepository;
    }

    public Message saveMessage(String senderCitizenId, String receiverCitizenId, String content) {
        // Obtener números de teléfono desde los citizenid
        String senderPhone = usuarioRepository.findById(senderCitizenId)
                .orElseThrow().getPhoneNumber();

        String receiverPhone = usuarioRepository.findById(receiverCitizenId)
                .orElseThrow().getPhoneNumber();

        Message message = new Message();
        message.setSenderPhone(senderPhone);
        message.setReceiverPhone(receiverPhone);
        message.setContent(content);

        Message savedMessage = messageRepository.save(message);

        // Enviar mensaje a ambos usuarios
        messagingTemplate.convertAndSendToUser(
                senderPhone,
                "/queue/messages",
                savedMessage
        );
        System.out.println("Mensaje enviado a: " + senderPhone);
        messagingTemplate.convertAndSendToUser(
                receiverPhone,
                "/queue/messages",
                savedMessage
        );
        System.out.println("Mensaje recibido a: " + receiverPhone);

        return savedMessage;
    }

    public List<Message> getConversation(String phone1, String phone2) {
        return messageRepository.findConversation(phone1, phone2);
    }
}