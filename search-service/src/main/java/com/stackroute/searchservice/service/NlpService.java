package com.stackroute.searchservice.service;

import com.stackroute.searchservice.domain.Question;

import java.util.List;

public interface NlpService {
    List<Question> filterAndFindQuestion(String query, String type);
}
