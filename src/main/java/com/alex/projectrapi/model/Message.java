package com.alex.projectrapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_phone")
    private String senderPhone;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "`read`") // Escapa el nombre con comillas invertidas
    private boolean read;

    public Message() {
        this.createdAt = new Date();
        this.read = false;
    }
}