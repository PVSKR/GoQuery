package com.stackroute.searchservice.service;

import com.stackroute.searchservice.controller.NLPController;
import com.stackroute.searchservice.domain.Question;
import com.stackroute.searchservice.domain.Type;
import com.stackroute.searchservice.repository.QuestionRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NlpServiceImpl implements NlpService{
    private StanfordCoreNLP stanfordCoreNLP;
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(NlpServiceImpl.class);


    @Autowired
    public NlpServiceImpl(StanfordCoreNLP stanfordCoreNLP, QuestionRepository questionRepository) {
        this.stanfordCoreNLP=stanfordCoreNLP;
        this.questionRepository=questionRepository;
    }
    @Override
    public List<Question> filterAndFindQuestion(String query, String type) {
        List<String> result=new ArrayList<>();
        CoreDocument coreDocument = new CoreDocument(query);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabels = coreDocument.tokens();
        List<String> typeList = null;

        if (!(type == null || type.isEmpty())) {
            type = type.trim();
            typeList = Arrays.asList(type.split(","));
            result=collectList(coreLabels, typeList);
            System.out.println("Filter Result of NLP service: "+result);
            List<Question> questionList = questionRepository.findQuestion(result.toString());
            return questionList;
        }
        else {
            logger.error("Question with specified query doesn't exist");
            return new ArrayList<>();
        }
    }

    private List<String> collectList(List<CoreLabel> coreLabels, List<String> type1) {
        List<Type> entity=new ArrayList<>();
        for(String w:type1)
        {
            entity.add(Type.valueOf(w));
        }
        List<String> keys=new ArrayList<>();
        for(Type t:entity){
            keys.addAll(coreLabels
                    .stream()
                    .filter(coreLabel -> t.getName().equalsIgnoreCase(coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class)))
                    .map(CoreLabel::originalText)
                    .collect(Collectors.toList()));
        }
        return keys;
    }
}
