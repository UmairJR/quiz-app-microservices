package com.umairjr.questionservice.service;

import com.umairjr.questionservice.model.Question;
import com.umairjr.questionservice.dao.QuestionDao;
import com.umairjr.questionservice.model.QuestionWrapper;
import com.umairjr.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category){
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question){
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to insert question.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> generateQuestions(String category, Integer numQs) {
        List<Integer> questionIds = questionDao.findRandomByCategory(category, numQs);
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(List<Integer> questionIds) {
        List<QuestionWrapper> qw = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer id: questionIds){
            Question q = questionDao.findById(id).get();
            questions.add(q);
        }
        for(Question q: questions){
            QuestionWrapper wrapper = new QuestionWrapper(q.getId(),q.getQuestion_title(), q.getOption1(), q.getOption2(),q.getOption3(), q.getOption4());
//            wrapper.setId(q.getId());
//            wrapper.setQuestion_title(q.getQuestion_title());
//            wrapper.setOption1(q.getOption1());
//            wrapper.setOption2(q.getOption2());
//            wrapper.setOption3(q.getOption3());
//            wrapper.setOption4(q.getOption4());
            qw.add(wrapper);
        }
        return new ResponseEntity<>(qw, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(List<Response> responses) {
        int rightAns = 0;
        for(Response r : responses){
            Question q = questionDao.findById(r.getId()).get();
            if(r.getUserAnswer().equals(q.getCorrect_option())){
                rightAns++;
            }
        }
        return new ResponseEntity<>(rightAns, HttpStatus.OK);
    }
}
