package com.example.demo.model;

public class ConversationUserDTO {
    private long id;
    private long userId;
    private long conversationId;

    public ConversationUserDTO(long id, long userId, long conversationId) {
        this.id = id;
        this.userId = userId;
        this.conversationId = conversationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }
}
