package com.stackroute.searchservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Question {
    private String uuidQuestionId;
    private String strQuestionerEmail;
    private String strQuestionTitle;
    private ArrayList<String> arrQuestionTags;
    private int intQuestionUpvote;
    private int intQuestionDownvote;
    private boolean acceptedAnswer;
}
