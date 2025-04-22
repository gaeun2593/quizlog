package com.mtvs.quizlog.domain.quiz.service;

import com.mtvs.quizlog.domain.quiz.dto.CreateQuizDTO;
import com.mtvs.quizlog.domain.quiz.dto.UpdateQuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional
    public CreateQuizDTO createQuiz(CreateQuizDTO createQuizDTO) {
        Optional<Quiz> findQuiz = quizRepository.findByTitle((createQuizDTO.getTitle()));

        if(findQuiz.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈 제목입니다. : " + createQuizDTO.getTitle());
        }

        Quiz quiz = new Quiz(createQuizDTO.getTitle(), createQuizDTO.getAnswer());
        Quiz saveQuiz =quizRepository.save(quiz);
        return new CreateQuizDTO(saveQuiz.getTitle(),saveQuiz.getAnswer());

    }

    @Transactional
    public UpdateQuizDTO updateQuiz(Long quizId, CreateQuizDTO createQuizDTO) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<Quiz> findQuiz = quizRepository.findByTitleAndIdNot(createQuizDTO.getTitle(), quizId);
        if(findQuiz.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재 합니다.");
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
