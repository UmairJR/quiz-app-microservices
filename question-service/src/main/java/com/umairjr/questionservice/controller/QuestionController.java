package com.umairjr.questionservice.controller;

import com.umairjr.questionservice.model.Question;
import com.umairjr.questionservice.model.QuestionWrapper;
import com.umairjr.questionservice.model.Response;
import com.umairjr.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
    
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQs){
        return questionService.generateQuestions(category, numQs);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsForQuiz(questionIds);
    }

    @PostMapping("calculate")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses){
        return questionService.calculateScore(responses);
    }



}
