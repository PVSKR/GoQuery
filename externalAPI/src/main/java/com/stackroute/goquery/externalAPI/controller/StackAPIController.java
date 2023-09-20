package com.stackroute.goquery.externalAPI.controller;

import com.stackroute.goquery.externalAPI.service.StackAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StackAPIController {
    private final StackAPI stackAPI;
    @Autowired
    public StackAPIController(StackAPI stackAPI) {
        this.stackAPI = stackAPI;
    }
    @GetMapping("/link/{title}")
    public String eternalAPI(@PathVariable String title){
        String url = "https://api.stackexchange.com/2.3/similar?order=desc&sort=activity&title=" + title + "&site=stackoverflow";
        return stackAPI.consumeAPI(url);
    }
}
