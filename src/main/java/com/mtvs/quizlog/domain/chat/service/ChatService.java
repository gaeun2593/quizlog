package com.mtvs.quizlog.domain.chat.service;

import com.mtvs.quizlog.domain.chat.dto.ChatMessageDTO;
import com.mtvs.quizlog.domain.chat.dto.ChatRoomPreviewDTO;
import com.mtvs.quizlog.domain.chat.entity.ChatMessage;
import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.chat.entity.ChatRoomStatus;
import com.mtvs.quizlog.domain.chat.repository.ChatMessageRepository;
import com.mtvs.quizlog.domain.chat.repository.ChatRoomRepository;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    // 메세지 저장
    @Transactional
    public ChatMessage saveMessage(ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = findChatRoomById(chatMessageDTO.getChatRoomId());
        User sender = findUserByNickname(chatMessageDTO.getSenderNickname());

        ChatMessage message = new ChatMessage(
                chatRoom,
                sender,
                chatMessageDTO.getMessage(),
                LocalDateTime.now(),
                chatMessageDTO.getMessageType()
        );

        ChatMessage savedMessage = chatMessageRepository.save(message);
        log.info(">>> 메시지 저장됨: {}", savedMessage);


        // 채팅방의 마지막 메세지 시간 업데이트
        chatRoom.setLastMessageTime(savedMessage.getCreatedAt());
        chatRoomRepository.save(chatRoom);

        return savedMessage;
    }

    // 문의하기를 눌렀을 때 채팅방이 있으면 가져오고 없으면 생성
    @Transactional
    public ChatRoom createOrGetChatRoom(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        Optional<ChatRoom> openRoom = chatRoomRepository.findByUser_UserIdAndStatus(userId, ChatRoomStatus.OPEN);

        if (openRoom.isPresent()) {
            // 이미 열려있는 채팅방이 있으면 리턴
            return openRoom.get();
        } else {
            // 없으면 새로 만들기
            ChatRoom newRoom = new ChatRoom(user);
            newRoom.setCreatedAt(LocalDateTime.now());
            newRoom.setStatus(ChatRoomStatus.OPEN);
            newRoom.setLastMessageTime(LocalDateTime.now());

            return chatRoomRepository.save(newRoom);
        }
    }

    // 채팅방 목록 조회
    @Transactional
    public Page<ChatRoomPreviewDTO> getAllChatRooms(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "id"));

        Page<ChatRoom> chatRooms;

        chatRooms =
                (keyword == null || keyword.isBlank()) ?
                        chatRoomRepository.findAll(pageable) :
                        chatRoomRepository.findByKeyword(keyword, pageable);

        return chatRooms.map(chatRoom -> {
            String latestMessage = chatMessageRepository
                    .findTopByChatRoomOrderByCreatedAtDesc(chatRoom)
                    .map(ChatMessage::getMessage)
                    .orElse("메시지가 없습니다.");

            return new ChatRoomPreviewDTO(
                    chatRoom.getId(),
                    chatRoom.getUser().getNickname(),
                    latestMessage,
                    chatRoom.getCreatedAt(),
                    chatRoom.getStatus()
            );
        });
    } //fe

    // 특정 채팅 방 메세지 내용 조회
    public List<ChatMessageDTO> getMessagesByChatRoomId(Long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoom_IdOrderByCreatedAtAsc(chatRoomId);

        // 엔티티를 DTO로 변환
        return chatMessages.stream()
                .map(chatMessage -> new ChatMessageDTO(
                        chatMessage.getChatRoom().getId(),
                        chatMessage.getSender().getNickname(),
                        chatMessage.getMessage(),
                        chatMessage.getType()))
                .collect(Collectors.toList());
    }


    // 채팅방 id로 채팅방 찾기
    public ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. id = " + chatRoomId));
    }

    // 닉네임으로 유저 찾기
    public User findUserByNickname(String name) {
        return userRepository.findByNickname(name)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. nickname = " + name));
    }

}
