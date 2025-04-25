package com.mtvs.quizlog.domain.quiz.service;

import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.quiz.dto.CreateQuizDTO;
import com.mtvs.quizlog.domain.quiz.dto.UpdateQuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final ChapterRepository chapterRepository;


    public void  createQuiz(User user , QuizForm quizForm , Chapter chapter ) {
        //Optional<Quiz> findQuiz = quizRepository.findByTitle((createQuizDTO.getTitle()));
        /*if(findQuiz.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 문제 입니다. : " + createQuizDTO.getTitle());
        }*/
       // Chapter chapter =chapterRepository.findById(chapterId).orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다: "+chapterId));
       // Quiz quiz = new Quiz(createQuizDTO.getTitle(), createQuizDTO.getAnswer(),chapterId);
        //Quiz saveQuiz =quizRepository.save(quiz);
        Quiz quiz = Quiz.createQuiz(user,chapter, quizForm.getWord(), quizForm.getAnswer(), LocalDateTime.now(), LocalDateTime.now());
        quizRepository.save(quiz);

    }

    public UpdateQuizDTO updateQuiz(Long quizId, CreateQuizDTO createQuizDTO) {

       // Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

       // Optional<Quiz> findQuiz = quizRepository.findByTitleAndIdNot(createQuizDTO.getTitle(), quizId);
       // if(findQuiz.isPresent()) {
       //     throw new IllegalArgumentException("중복되는 문제가 존재 합니다.");
       // }

     /*    quiz = Quiz.builder()
                    .title(createQuizDTO.getTitle())
                    .answer(createQuizDTO.getAnswer())
                    .build();*/
       /* Quiz savedQuiz = quizRepository.save(quiz);*/

        return new UpdateQuizDTO();
    }

    public void deleteQuiz(Long quizId) {
       // boolean result = quizRepository.existsById(quizId);

     //  if(!result) {
      //      throw new IllegalArgumentException("게시글이 존재하지 않습니다. " + quizId);
     //   }
      //  quizRepository.deleteById(quizId);
    }




}
