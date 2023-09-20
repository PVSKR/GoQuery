package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.domain.Question;
import com.stackroute.searchservice.repository.QuestionRepository;
import com.stackroute.searchservice.service.NlpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("api/v1/nlp")
public class NLPController {
    private NlpService nlpService;
    private QuestionRepository questionRepository;

    @Autowired
    public NLPController(NlpService nlpService, QuestionRepository questionRepository) {
        this.nlpService=nlpService;
        this.questionRepository = questionRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(NLPController.class);

    @PostMapping("/insert")
    public ResponseEntity<Question> insertQuestion(@RequestBody Question question) {
        try {
            return new ResponseEntity<>(questionRepository.insertQuestion(question), HttpStatus.CREATED);
        } catch(Exception exc) {
            logger.error("Error occurred while saving question");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        try {
            return new ResponseEntity<>(questionRepository.getAllQuestions(), HttpStatus.OK);
        } catch(Exception exc) {
            logger.error("Error occurred while getting all questions");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateQuestionById(@RequestBody Question question, @PathVariable String id) {
        try {
            return  new ResponseEntity<>(questionRepository.updateQuestionById(id, question), HttpStatus.OK);
        } catch(Exception exc) {
            logger.error("Error occurred while updating question");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Question>> findQuestionByQuery(@PathVariable String query){
        try {
            return new ResponseEntity<>(questionRepository.findQuestion(query), HttpStatus.OK);
        } catch (Exception exc){
            logger.error("Error while fetching question basis query");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/filter/{input}")
    public ResponseEntity<?> nlpFilter(@PathVariable final String input, @RequestParam("type") String type) {
        logger.info("In NLP Service Controller(Your inputs):"+input+" and Type : "+type);
        List<Question> resultQuestion = new ArrayList<>();
        try {
            resultQuestion = nlpService.filterAndFindQuestion(input, type);
            return new ResponseEntity<>(resultQuestion, HttpStatus.OK);
        } catch(Exception exc){
            return new ResponseEntity<>(resultQuestion, HttpStatus.CONFLICT);
        }
    }
}
