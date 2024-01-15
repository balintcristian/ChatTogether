package com.example.demo.model;

public class ConversationDTO {
    private long id;
    private String conversationName;

    public ConversationDTO(long id, String conversationName) {
        this.id = id;
        this.conversationName = conversationName;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getConversationName() {
        return conversationName;
    }
    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }
}
