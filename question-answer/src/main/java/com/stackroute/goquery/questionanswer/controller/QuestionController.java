package com.stackroute.goquery.questionanswer.controller;

import com.stackroute.goquery.questionanswer.domain.Question;
import com.stackroute.goquery.questionanswer.exception.QuestionAlreadyExists;
import com.stackroute.goquery.questionanswer.exception.QuestionNotFoundException;
import com.stackroute.goquery.questionanswer.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
@CrossOrigin(value="*")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/addquestion")
    public ResponseEntity<?> addNewQuestion(@RequestBody Question question){
        try{
            return new ResponseEntity<Question>(questionService.addNewQuestion(question), HttpStatus.CREATED);
        }
        catch (QuestionAlreadyExists qae){
            return new ResponseEntity<>("Same type of question exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getquestions")
    public ResponseEntity<?> getAllQuestions(){
        return new ResponseEntity<List<Question>>(questionService.getAllquestions(), HttpStatus.OK);
    }


    @GetMapping("/questionbyid/{id}")
    public Question getQuestionById(@PathVariable UUID id){
        return questionService.getQuestionById(id);
    }



    @DeleteMapping("/deletequestion/{id}")
    public String deleteQuestion(@PathVariable UUID id){
            Question question = questionService.getQuestionById(id);
            questionService.deleteQuestionById(id);
            return "Question removed successfully";
    }


    @PutMapping("/putquestions")
    public ResponseEntity updateQuestion(@RequestBody Question question) {
        questionService.updateQuestion(question);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Question>> getQuestionByName(@PathVariable String email){
        return new ResponseEntity(questionService.getQuestionByEmail(email), HttpStatus.OK);
    }
}
