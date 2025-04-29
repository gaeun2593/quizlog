package com.mtvs.quizlog.domain.chat.dto;

import com.mtvs.quizlog.domain.chat.entity.ChatRoomStatus;

import java.time.LocalDateTime;

public class ChatRoomPreviewDTO {
    // 채팅방 목록에 필요한 정보 DTO

    private Long chatRoomId;
    private String senderNickname;
    private String latestMessage; // 마지막 메세지
    private LocalDateTime createdAt; // 채탱방 생성 시간
    private ChatRoomStatus status;

    public ChatRoomPreviewDTO() {}

    public ChatRoomPreviewDTO(Long chatRoomId, String senderNickname, String latestMessage, LocalDateTime createdAt, ChatRoomStatus status) {
        this.chatRoomId = chatRoomId;
        this.senderNickname = senderNickname;
        this.latestMessage = latestMessage;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ChatRoomStatus getStatus() {
        return status;
    }

    public void setStatus(ChatRoomStatus status) {
        this.status = status;
    }
}
