package com.mtvs.quizlog.domain.chat.repository;

import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.chat.entity.ChatRoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findById(Long chatRoomId);
    Optional<ChatRoom> findByUser_UserIdAndStatus(Long userId, ChatRoomStatus status);
}
