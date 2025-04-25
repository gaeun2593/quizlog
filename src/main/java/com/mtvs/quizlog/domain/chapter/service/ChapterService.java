package com.mtvs.quizlog.domain.chapter.service;


import com.mtvs.quizlog.domain.chapter.dto.ConvertEntityToDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.UpdateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.response.ResponseCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Status;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* 테스트 추가해주세요!!!!
*
*
* */

@Service
@RequiredArgsConstructor
@Transactional
public class ChapterService{

    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;


    //챕터 생성
    public Chapter createChapter(RequestCreateChapterDTO requestCreateChapterDTO , User user) {
        Chapter chapter = Chapter.createChapter(user,requestCreateChapterDTO.getTitle(), requestCreateChapterDTO.getDescription(), LocalDateTime.now(), LocalDateTime.now());
        chapterRepository.save(chapter);
        return chapter;

//        findById로 user엔티티를 user 객체로 변환. 그걸로 chapter 외래키 초기화/
        /*User user=  userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다: "+userId));*/

/*
    Auth-> userId
    userId-> 를 왜?
    chapter의 user를 받고싶기때문이다. 왜?
    user를 findId로 받고!  user객체를 받아서 전달!! 초기화.
   findbyId -> user
*
*
*
*
* */

/*        Chapter chapter = Chapter.builder()
                        .title(requestCreateChapterDTO.getTitle())
                        .description(requestCreateChapterDTO.getDescription())
                        .status(Status.ACTIVE)
//                user_id 외래키를 위한 user, //위 findById로 변환한 user
                        .user(user)
                        .build();*/

    /*   // Chapter saveChapter = chapterRepository.save(chapter);
        String savedTitle = saveChapter.getTitle();
        String savedDescription = saveChapter.getDescription();

        *//*getId() => chapter Id //// NOT userId!!!!!*//*
        Long chapterId = saveChapter.getId();*/

    }

    @Transactional
    public UpdateChapterDTO updateChapter(Long ChapterId, RequestCreateChapterDTO requestCreateChapterDTO) {

      /*  Chapter chapter = chapterRepository.findById(ChapterId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<Chapter> findChapter = chapterRepository.findByTitleAndIdNot(requestCreateChapterDTO.getTitle(), ChapterId);
        if(findChapter.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재 합니다.");
        }*/

/*
        chapter = Chapter.builder()
                .title(requestCreateChapterDTO.getTitle())
                .description(requestCreateChapterDTO.getDescription())
                .build();
        Chapter savedChapter = chapterRepository.save(chapter);
*/

        return new UpdateChapterDTO();
    }

    @Transactional
    public void deleteChapter(Long chapterId) {
      /*  boolean result = chapterRepository.existsById(chapterId);

        if(!result) {
            throw new IllegalArgumentException("챕터가 존재하지 않습니다. " + chapterId);
        }
        chapterRepository.deleteById(chapterId);*/
    }

    public List<GetChapterDTO> getChapterDTOList(){
       // List<Chapter> getChapterList = chapterRepository.findAll();
       /* List<GetChapterDTO> getChapterDTOList = new ArrayList<>();

        //        Chapter->GetChapterDTO 변환
        //        GetChapterDTO :id,title,description
        //        Chapter: id,title,description,creation date
        ConvertEntityToDTO convertEntityToDTO = new ConvertEntityToDTO();
        convertEntityToDTO.GetChapterDTOToChapter(getChapterList,getChapterDTOList);
        return getChapterDTOList;*/
        return new ArrayList<>();

    }


/*
*
* 테스트주석입니다.
* */

}
