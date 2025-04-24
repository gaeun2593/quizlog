package com.mtvs.quizlog.domain.chapter.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter

public class ResponseCreateChapterDTO {
    private Long userId;
    private Long chapterId;
    @NotBlank(message = "챕터 제목은 필수 입력 값입니다.")
    @Size(min = 1, max = 100, message = "제목은 1글자 이상 또는 100글자 미만입니다.")
    private String title;

    @NotBlank(message = "챕터 설명은 필수 입력 값입니다.")
    @Size(min =1 , max = 255, message = "설명은 1글자 이상 또는 255글자 미만입니다.")
    private String description;

    public Long getUserId() { return userId; }

    public Long getChapterId() { return chapterId; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
