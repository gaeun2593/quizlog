package com.mtvs.quizlog.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.http.converter.json.GsonBuilderUtils;

@Entity
public class User {
    @Id
    private String id;
}
