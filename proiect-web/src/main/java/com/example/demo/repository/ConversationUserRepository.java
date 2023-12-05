package com.example.demo.repository;

import com.example.demo.persistence.ConversationUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ConversationUserRepository extends CrudRepository<ConversationUser, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ConversationUser cu WHERE cu.conversationId = :conversationId AND cu.userId = :userId")
    void deleteByConversationIdAndUserId(@Param("conversationId") Long conversationId, @Param("userId") Long userId);
}
