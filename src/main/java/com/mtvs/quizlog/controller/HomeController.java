package com.mtvs.quizlog.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class HomeController {

    public String home(){
        return "index";
    }
}
