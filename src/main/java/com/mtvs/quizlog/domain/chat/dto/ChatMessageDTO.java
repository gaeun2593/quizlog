package com.mtvs.quizlog.domain.chat.dto;

import com.mtvs.quizlog.domain.chat.entity.MessageType;

public class ChatMessageDTO {
// 채팅방 안에서 메세지를 주고 받을 때

    private Long chatRoomId; // 채팅방 Id
    private String senderNickname; // 보낸 사람 닉네임
    private String message; // 내용
    private MessageType messageType; // TALK, ENTER, LEAVE

    // 기본 생성자 x - 채팅 정렬 로직에서 참조 오류 발생

    public ChatMessageDTO(Long chatRoomId, String senderNickname, String message, MessageType messageType) {
        this.chatRoomId = chatRoomId;
        this.senderNickname = senderNickname;
        this.message = message;
        this.messageType = messageType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }


}
