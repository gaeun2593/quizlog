package com.mtvs.quizlog.domain.quiz.service;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.quiz.dto.CreateQuizDTO;
import com.mtvs.quizlog.domain.quiz.dto.UpdateQuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {
/*
* 만들기 누른다
* 챕터 생성!
* 퀴즈생성!!
* 퀴즈생성!!
* -> 이때 챕터부터 생성되게한다
* 챕터가 생성되며 자신의 ID를 남긴다
* 퀴즈생성이 그ID를 받아 생성된다.
*
* */
    private final QuizRepository quizRepository;
    private final ChapterRepository chapterRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, ChapterRepository chapterRepository) {
        this.quizRepository = quizRepository;
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    public CreateQuizDTO createQuiz(CreateQuizDTO createQuizDTO, Long chapterId) {
        //챕터id->챕터로 (외래키때문)
        Chapter chapter =chapterRepository.findById(chapterId).orElseThrow(()->new IllegalArgumentException("해당 챕터가 존재하지 않습니다: "+chapterId));
        List<Quiz> saveQuizList = new ArrayList<>();
        createQuizDTO.getQuizList().forEach(DTOsQuiz -> {
           /*
           * DTO내의 QUIZ.
           * 다시 챕터id 넘겨줌
           * */
           Quiz quiz = Quiz.builder()
                   .title(DTOsQuiz.getTitle())
                   .answer(DTOsQuiz.getAnswer())
                   .chapter(chapter)
                   .build();
           Quiz saveQuiz = quizRepository.save(quiz);
            saveQuizList.add(saveQuiz);
       });


        return new CreateQuizDTO(saveQuizList);

    }

    @Transactional
    public UpdateQuizDTO updateQuiz(Long quizId, CreateQuizDTO createQuizDTO) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<Quiz> findQuiz = quizRepository.findByTitleAndIdNot(createQuizDTO.getTitle(), quizId);
        if(findQuiz.isPresent()) {
            throw new IllegalArgumentException("중복되는 문제가 존재 합니다.");
        }

         quiz = Quiz.builder()
                    .title(createQuizDTO.getTitle())
                    .answer(createQuizDTO.getAnswer())
                    .build();
        Quiz savedQuiz = quizRepository.save(quiz);

        return new UpdateQuizDTO(savedQuiz.getId(), savedQuiz.getTitle(), savedQuiz.getAnswer());
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        boolean result = quizRepository.existsById(quizId);

        if(!result) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. " + quizId);
        }
        quizRepository.deleteById(quizId);
    }




}
