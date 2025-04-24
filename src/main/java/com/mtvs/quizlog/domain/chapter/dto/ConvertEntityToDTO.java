package com.mtvs.quizlog.domain.chapter.dto;

import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;

import java.util.List;


/*
* 엔티티->DTO 변환을 위한 클래스
* */
public class ConvertEntityToDTO {

    public List<GetChapterDTO> GetChapterDTOToChapter(List<Chapter> chapterList, List<GetChapterDTO> getChapterDTOList) {
        for(GetChapterDTO chapter : getChapterDTOList) {
            long id = chapter.getId();
            String title = chapter.getTitle();
            String description = chapter.getDescription();
            GetChapterDTO getChapterDTO = new GetChapterDTO(id, title, description);
            getChapterDTOList.add(getChapterDTO);
        }
        return getChapterDTOList;
    }

}
