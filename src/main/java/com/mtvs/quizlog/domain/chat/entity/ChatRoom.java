package com.mtvs.quizlog.domain.chat.entity;

import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id") // 채팅방 ID
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 문의를 한 유저 (학생 or 선생님)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt; // 24시간 이후 자동 종료

    private LocalDateTime lastMessageTime;

    @Enumerated(EnumType.STRING) // OPEN, CLOSED
    private ChatRoomStatus status;

    // 기본 생성자 ?
    protected ChatRoom() {}

    public ChatRoom(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.status = ChatRoomStatus.OPEN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public ChatRoomStatus getStatus() {
        return status;
    }

    public void setStatus(ChatRoomStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
