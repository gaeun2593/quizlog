package com.mtvs.quizlog.domain.chat.entity;

import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id") // 해당 메세지가 속한 채팅방
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender; // 메세지를 보낸 사람 (학생, 선생님, 관리자)

    private String message;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MessageType type; // TALK, ENTER, LEAVE

    protected ChatMessage() { }

    public ChatMessage(ChatRoom chatRoom, User sender, String message, LocalDateTime createdAt, MessageType type) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
