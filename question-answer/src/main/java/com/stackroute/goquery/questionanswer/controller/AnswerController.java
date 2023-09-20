package com.stackroute.goquery.questionanswer.controller;

import com.stackroute.goquery.questionanswer.domain.Answer;
import com.stackroute.goquery.questionanswer.domain.Comment;
import com.stackroute.goquery.questionanswer.domain.Question;
import com.stackroute.goquery.questionanswer.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/answers")
@CrossOrigin(value="*")
public class AnswerController {
    AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/postanswers/{id}")
    public ResponseEntity<?> addNewAnswer(@RequestBody Answer answer,@PathVariable UUID id){
              return new ResponseEntity<Answer>(answerService.addNewAnswer(answer, id), HttpStatus.CREATED);
    }

    @GetMapping("/getanswers/{questionId}")
    public ResponseEntity<?> getAnswer(@PathVariable UUID questionId){
        return new ResponseEntity<List<Answer>>(answerService.getAllAnswers(questionId), HttpStatus.OK);
    }

    @PutMapping("/putanswers/{questionId}")
    public ResponseEntity updateAnswer(@RequestBody Answer answer, @PathVariable UUID questionId) {
        answerService.updateAnswer(answer, questionId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/upvote/{answerId}")
    public ResponseEntity<?> upvoteAnswer(@PathVariable UUID answerId) {
        return new ResponseEntity<>(answerService.upvoteAnswer(answerId), HttpStatus.OK);
    }

    @PutMapping("/downvote/{answerId}")
    public ResponseEntity<?> downVoteAnswer(@PathVariable UUID answerId) {
        return new ResponseEntity<>(answerService.downvoteAnswer(answerId), HttpStatus.OK);
    }

    @PutMapping("/acceptAnswer/{answerId}")
    public ResponseEntity<?> acceptAnswer(@PathVariable UUID answerId) {
        return new ResponseEntity<>(answerService.acceptAnswer(answerId), HttpStatus.OK);
    }

    @PutMapping("/comment/{answerId}")
    public ResponseEntity<?> commentAnswer(@PathVariable UUID answerId, @RequestBody Comment commentGiven) {
        return new ResponseEntity<>(answerService.commentAnswer(answerId, commentGiven), HttpStatus.OK);
    }
    @GetMapping("/allcomments/{answerId}")
    public ResponseEntity<?> showAllComments(@PathVariable UUID answerId){
        return new ResponseEntity<List<Comment>>(answerService.showAllComments(answerId),HttpStatus.OK);
    }

    @GetMapping("/{mail}")
    public ResponseEntity<?> getAnswersByMail(@PathVariable String mail){
        return new ResponseEntity<>(answerService.getAnswerByMail(mail), HttpStatus.OK);
    }

}
