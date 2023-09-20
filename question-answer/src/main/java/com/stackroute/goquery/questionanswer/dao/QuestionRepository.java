package com.stackroute.goquery.questionanswer.dao;

import com.stackroute.goquery.questionanswer.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends MongoRepository<Question, UUID> {

    List<Question> findByStrQuestionerEmail(String email);
}
