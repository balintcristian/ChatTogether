package com.example.demo.model;

import java.sql.Timestamp;

public class MessageDTO {
    private long id;
    private long conversationId;
    private long userId;
    private String messageText;
    private Timestamp sentDatetime;

    public MessageDTO(long id, long conversationId, long userId, String messageText, Timestamp sentDatetime) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.messageText = messageText;
        this.sentDatetime = sentDatetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getSentDatetime() {
        return sentDatetime;
    }

    public void setSentDatetime(Timestamp sentDatetime) {
        this.sentDatetime = sentDatetime;
    }
}
