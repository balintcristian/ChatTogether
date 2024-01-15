package com.example.demo.repository;

import com.example.demo.persistence.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT message FROM Message message WHERE message.conversationId = :conversationId")
    List<Message> findByConversationId(@Param("conversationId") Long conversationId);
}
