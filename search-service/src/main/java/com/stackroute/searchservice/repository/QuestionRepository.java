package com.stackroute.searchservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stackroute.searchservice.domain.Question;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class QuestionRepository {

    private final String INDEX = "questiondata";
    private final String TYPE = "questions";

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepository.class);

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    @Autowired
    public QuestionRepository(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient=restHighLevelClient;
        this.objectMapper=objectMapper;
    }

    public Question insertQuestion(Question question) {
        Map<String, Object> dataMap = objectMapper.convertValue(question, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, question.getUuidQuestionId()).source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
            logger.error("error in inserting");
        } catch (java.io.IOException ex) {
            ex.getLocalizedMessage();
            logger.error("error in inserting");
        }
        return question;
    }

    public Map<String, Object> getAllQuestions() {
        GetRequest getRequest = new GetRequest();
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

    public Map<String, Object> updateQuestionById(String id, Question question) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true); // Fetch Object after its
        // update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update question");
        try {
            String bookJson = objectMapper.writeValueAsString(question);
            updateRequest.doc(bookJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
            return sourceAsMap;
        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        return error;
    }

    public List<Question> findQuestion(String text) {
        logger.info("Search text:"+text);
        try {
            SearchRequest request = new SearchRequest(INDEX);
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(text);
            scb.query(mcb);
            request.source(scb);

            SearchResponse response = restHighLevelClient.search(request);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<Question> questions = new ArrayList(searchHits.length);
            for (SearchHit hit : searchHits) {
                String sourceAsString = hit.getSourceAsString();
                if (sourceAsString != null) {
                    Gson gson = new Gson();
                    questions.add(gson.fromJson(sourceAsString, Question.class));
                }
            }
            logger.info("Size of questions found:"+questions.size());
            return questions;
        } catch (IOException ex) {
            logger.error("Error in finding question based on query");
        }
        return Collections.emptyList();
    }
}
