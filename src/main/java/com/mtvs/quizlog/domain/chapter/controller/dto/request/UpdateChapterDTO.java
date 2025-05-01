package com.mtvs.quizlog.domain.chapter.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChapterDTO {

    @NotBlank
    private Long id;

    @NotBlank(message = "챕터 제목은 필수 입력 값입니다.")
    @Size(min = 1, max = 100, message = "제목은 1글자 이상 또는 100글자 미만입니다.")
    private String title;

    @NotBlank(message = "챕터 설명은 필수 입력 값입니다.")
    @Size(min =1 , max = 255, message = "설명은 1글자 이상 또는 255글자 미만입니다.")
    private String description;
}
