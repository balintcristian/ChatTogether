package com.example.demo.persistence;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "conversation_id")
    private long conversationId;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "message_text")
    private String messageText;
    @CreationTimestamp
    @Column(name = "sent_datetime")
    private Timestamp sentDatetime;

    public Message() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && conversationId == message.conversationId && userId == message.userId && Objects.equals(messageText, message.messageText) && Objects.equals(sentDatetime, message.sentDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conversationId, userId, messageText, sentDatetime);
    }
}
