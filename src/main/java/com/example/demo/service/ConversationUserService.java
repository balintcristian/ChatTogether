package com.example.demo.service;

import com.example.demo.model.ConversationUserDTO;
import com.example.demo.persistence.ConversationUser;
import com.example.demo.repository.ConversationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationUserService {

    @Autowired
    private ConversationUserRepository conversationUserRepository;

    public void deleteByConversationIdAndUserId(Long conversationId, Long userId) {
        conversationUserRepository.deleteByConversationIdAndUserId(conversationId, userId);
    }

    public ConversationUserDTO inviteToConversation(ConversationUserDTO conversationUserDTO) {
        ConversationUser conversationUser = conversationUserRepository.save(convertDtoToEntity(conversationUserDTO));
        return convertEntityToDto(conversationUser);
    }

    private ConversationUser convertDtoToEntity(ConversationUserDTO conversationUserDTO) {
        ConversationUser conversationUser = new ConversationUser();
        conversationUser.setConversationId(conversationUserDTO.getConversationId());
        conversationUser.setUserId(conversationUserDTO.getUserId());
        return conversationUser;
    }

    private ConversationUserDTO convertEntityToDto(ConversationUser conversationUser) {
        return new ConversationUserDTO(conversationUser.getId(), conversationUser.getConversationId(),
                conversationUser.getUserId());
    }
}
