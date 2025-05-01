package com.mtvs.quizlog.domain.compare.service;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chat.dto.ChatMessageDTO;
import com.mtvs.quizlog.domain.chat.entity.ChatRoom;
import com.mtvs.quizlog.domain.chat.service.ChatService;
import com.mtvs.quizlog.domain.user.dto.LogInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompareController {

    private final CompareService compareService;
    private final ChatService chatService;


    @GetMapping ("/compare")
    public String enterChatRoom(@AuthenticationPrincipal AuthDetails userDetails,
                                      Model model) {


        LogInDTO logInDTO = userDetails.getLogInDTO();


        model.addAttribute("logInDTO",  logInDTO) ;
        return "compare";
    }

}
