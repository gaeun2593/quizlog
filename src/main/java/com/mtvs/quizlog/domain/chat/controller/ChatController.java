package com.mtvs.quizlog.domain.chat.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chat.dto.ChatMessageDTO;
import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.chat.entity.MessageType;
import com.mtvs.quizlog.domain.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // 메세지 보내기 -> /app/message
    @MessageMapping("/message")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        // 메세지 저장
        chatService.saveMessage(chatMessageDTO);

        // 해당 채팅방 구독자한테 메세지 보내기
        simpMessagingTemplate.convertAndSend("/topic/room/" + chatMessageDTO.getChatRoomId(), chatMessageDTO);
    }

    @PostMapping("/message")
    public String sendMessage(@RequestParam Long chatRoomId,
                              @RequestParam String message,
                              @AuthenticationPrincipal AuthDetails userDetails) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO(
                chatRoomId,
                userDetails.getLogInDTO().getNickname(),
                message,
                MessageType.TALK
        );
        chatService.saveMessage(chatMessageDTO);
        simpMessagingTemplate.convertAndSend("/topic/room/" + chatRoomId, chatMessageDTO);

        return "redirect:/chat/room/" + chatRoomId;
    }

    // 채팅방 불러오기 - 학생/선생님이 문의하기 버튼 눌렀을 때
    @PostMapping("/room")
    public String createOrGetChatRoom(@AuthenticationPrincipal AuthDetails userDetails, ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        ChatRoom chatRoom = chatService.createOrGetChatRoom(userId);

        model.addObject("chatRoom", chatRoom);
        return "redirect:/chat/room/" + chatRoom.getId();
    }

    @GetMapping ("/room/{roomId}")
    public ModelAndView enterChatRoom(@PathVariable Long roomId, ModelAndView model) {
        List<ChatMessageDTO> messages = chatService.getMessagesByChatRoomId(roomId);

        model.addObject("chatRoomId", roomId);
        model.addObject("messages", messages);
        model.setViewName("user/chat-admin");
        return model;
    }
}
