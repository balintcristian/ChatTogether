package com.example.demo.repository;

import com.example.demo.persistence.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
    List<Conversation> findAll();

    @Query("SELECT conversation FROM Conversation conversation JOIN conversation.participants user WHERE user.id = :userId")
    List<Conversation> findByUserId(@Param("userId") Long userId);

}
