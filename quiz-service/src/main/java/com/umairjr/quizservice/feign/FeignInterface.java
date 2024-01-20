package com.umairjr.quizservice.feign;

import com.umairjr.quizservice.model.QuestionWrapper;
import com.umairjr.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface FeignInterface {
    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQs);

    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds);

    @PostMapping("questions/calculate")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses);
}
