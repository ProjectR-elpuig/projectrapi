package com.alex.projectrapi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    // ID del chat
    private String chatId;
    // Tipo de mensaje
    private MessageType type;
    // Mensaje
    private String content;
    // Imagen Si es que hay
    private byte[] img;
    // Usuario que envia el mensaje
    private String senderCitizenId;
    // Usuario que recibe el mensaje
    private String receiverPhoneNumber;

}
