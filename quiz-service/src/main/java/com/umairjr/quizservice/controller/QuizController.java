package com.umairjr.quizservice.controller;

import com.umairjr.quizservice.model.QuestionWrapper;
import com.umairjr.quizservice.model.QuizDTO;
import com.umairjr.quizservice.model.Response;
import com.umairjr.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO qDto){
        return quizService.createQuiz(qDto.getQuiz_title(), qDto.getCategory(), qDto.getNumQs());
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable("id") int id){
        return quizService.getQuiz(id);
    }
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") int id, @RequestBody List<Response> responses){
        return quizService.submitQuiz(id, responses);
    }
}
