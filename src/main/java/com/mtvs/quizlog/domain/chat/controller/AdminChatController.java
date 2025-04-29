package com.mtvs.quizlog.domain.chat.controller;

import com.mtvs.quizlog.domain.chat.dto.ChatRoomPreviewDTO;
import com.mtvs.quizlog.domain.chat.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/chat")
public class AdminChatController {

    private final ChatService chatService;

    public AdminChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/rooms")
    public ModelAndView showChatRoomList(ModelAndView model) {
        List<ChatRoomPreviewDTO> chatRooms = chatService.getAllChatRooms();
        model.addObject("chatRooms", chatRooms);
        model.setViewName("admin/chat-list");
        return model;
    }

}
