package com.stackroute.goquery.questionanswer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Question")
public class Question {

    @Id
    private UUID uuidQuestionId;
    private String strQuestionerEmail;
    private String strQuestionTitle;
    private ArrayList<String> arrQuestionTags;
    private int intQuestionUpvote;
    private int intQuestionDownvote;
    private boolean acceptedAnswer;
    private ArrayList<Answer> answers;
}
