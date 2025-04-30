package com.mtvs.quizlog.domain.chat.repository;

import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.chat.entity.ChatRoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.user.nickname LIKE CONCAT('%', :keyword, '%')")
    Page<ChatRoom> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<ChatRoom> findById(Long chatRoomId);
    Optional<ChatRoom> findByUser_UserIdAndStatus(Long userId, ChatRoomStatus status);
}
