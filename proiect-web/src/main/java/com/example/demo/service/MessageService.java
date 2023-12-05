package com.example.demo.service;

import com.example.demo.model.MessageDTO;
import com.example.demo.persistence.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    public List<MessageDTO> findByConversationId(Long conversationId){
        return messageRepository.findByConversationId(conversationId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public MessageDTO saveMessage(MessageDTO messageDTO){
        Message message = messageRepository.save(convertDtoToEntity(messageDTO));
        return convertEntityToDto(message);
    }

    private MessageDTO convertEntityToDto(Message message){
        return new MessageDTO(message.getId(), message.getConversationId(), message.getUserId(), message.getMessageText(),
                message.getSentDatetime());
    }

    /**
     * Converteste un obiect MessageDTO in obiect de tip Message.
     * In momentul conversiei nu se seteaza id-ul, nici campul date_time deoarece e responsabilitatea
     * Serverului de baze de date sa initializeze acele campuri.
     */
    private Message convertDtoToEntity(MessageDTO messageDTO){
        Message message = new Message();
        message.setConversationId(messageDTO.getConversationId());
        message.setUserId(messageDTO.getUserId());
        message.setMessageText(messageDTO.getMessageText());
        return message;
    }
}
