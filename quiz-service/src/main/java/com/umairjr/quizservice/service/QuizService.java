package com.umairjr.quizservice.service;


import com.umairjr.quizservice.feign.FeignInterface;
import com.umairjr.quizservice.dao.QuizDao;
import com.umairjr.quizservice.model.QuestionWrapper;
import com.umairjr.quizservice.model.Quiz;
import com.umairjr.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    FeignInterface feign;
    public ResponseEntity<String> createQuiz(String quiz_title, String category, int numQs){
        List<Integer> questions = feign.generateQuestionsForQuiz(category,numQs).getBody();// call http://localhost:8081/questions/generate
        Quiz q = new Quiz();
        q.setQuiz_title(quiz_title);
        q.setQuestionIds(questions);
        try{
            quizDao.save(q);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to create a quiz", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if (quiz.isPresent()) {
            List<Integer> questionIds = quiz.get().getQuestionIds();
            List<QuestionWrapper> questions = feign.getQuestionsForQuiz(questionIds).getBody();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } else {
            String message = "Quiz with ID " + id + " not found in the database.";
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> responses) {
        try{
            Integer rightAns = feign.calculateScore(responses).getBody();
            return new ResponseEntity<>(rightAns,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(-1,HttpStatus.BAD_REQUEST);
    }
}
