package com.example.demo.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conversation_user", schema = "demo", catalog = "")
public class ConversationUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "conversation_id")
    private long conversationId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationUser that = (ConversationUser) o;
        return id == that.id && userId == that.userId && conversationId == that.conversationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, conversationId);
    }
}
