package com.mtvs.quizlog.domain.compare.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

    private int  NUMBER = 1 ;

    public int getNumber() {
        return NUMBER++;
    }
}
