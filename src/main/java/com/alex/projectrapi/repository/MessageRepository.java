package com.alex.projectrapi.repository;

import com.alex.projectrapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderPhone = :phone1 AND m.receiverPhone = :phone2) OR " +
            "(m.senderPhone = :phone2 AND m.receiverPhone = :phone1) " +
            "ORDER BY m.createdAt ASC")
    List<Message> findConversation(String phone1, String phone2);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderPhone = :phone1 AND m.receiverPhone = :phone2) OR " +
            "(m.senderPhone = :phone2 AND m.receiverPhone = :phone1) " +
            "ORDER BY m.createdAt DESC LIMIT 1")
    Message findLastMessageBetween(@Param("phone1") String phone1, @Param("phone2") String phone2);
}