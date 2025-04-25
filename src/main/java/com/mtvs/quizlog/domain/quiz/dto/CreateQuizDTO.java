package com.mtvs.quizlog.domain.quiz.dto;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizDTO {

    private Long chapterId;
    @NotBlank(message = "문제는 필수 입력 값입니다.")
    @Size(min = 2, max = 100, message = "문제은 2글자 이상 또는 100글자 미만입니다.")
    private String title;


    @NotBlank(message = "답은 필수 입력 항목입니다.")
    @Size(min = 1, max = 100, message = "답은 1자 이상 100자 이하로 입력해주세요")
    private String answer;


}
