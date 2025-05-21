package com.alex.projectrapi.service;

import com.alex.projectrapi.model.Message;
import com.alex.projectrapi.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(String senderPhone, String receiverPhone, String content) {
        Message message = new Message();
        message.setSenderPhone(senderPhone);
        message.setReceiverPhone(receiverPhone);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public List<Message> getConversation(String phone1, String phone2) {
        return messageRepository.findConversation(phone1, phone2);
    }
}