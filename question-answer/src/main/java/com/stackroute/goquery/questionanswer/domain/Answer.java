package com.stackroute.goquery.questionanswer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    UUID intAnswerId;
    String strAnswererEmail;
    String strAnswerStatement;
    int intAnswerUpvote;
    int intAnswerDownvote;
    boolean accepted;
    List<Comment> comments;


}




