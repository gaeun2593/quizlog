package com.mtvs.quizlog.domain.chat.repository;

import com.mtvs.quizlog.domain.chat.entity.ChatMessage;
import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.user.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Optional<ChatMessage> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);
    List<ChatMessage> findByChatRoom_IdOrderByCreatedAtAsc(Long chatRoomId);
}
