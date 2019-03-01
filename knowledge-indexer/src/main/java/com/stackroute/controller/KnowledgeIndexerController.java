package com.stackroute.controller;

import com.stackroute.model.Knowledge;
import com.stackroute.service.KnowledgeIndexerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@Slf4j
public class KnowledgeIndexerController {


    Logger logger = Logger.getLogger(KnowledgeIndexerController.class.getName());


    private KnowledgeIndexerService knowledgeIndexerService;

    @Autowired
    KnowledgeIndexerController(KnowledgeIndexerService knowledgeIndexerService) {
        this.knowledgeIndexerService = knowledgeIndexerService;
    }

    //Code used for testing the methods
    @PostMapping("/addKnowledge")
    public ResponseEntity<String> addKnowledge(@RequestBody Knowledge knowledge) {
        ResponseEntity<String> responseEntity;
        try {
            knowledgeIndexerService.saveKnowledgeToDb(knowledge);
            responseEntity = new ResponseEntity<>("Knowledge saved sucessfully", HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error while saving knowledge", HttpStatus.BAD_GATEWAY);
        }
        return responseEntity;
    }

    //Code used for testing the methods
    @PostMapping("addRelationship/{name}/{paragraphId}/{intentLevel}/{confidenceScore}")
    public ResponseEntity<String> addRelationship(@PathVariable("name") String name,
                                                  @PathVariable("paragraphId")String paragraphId,
                                                  @PathVariable("intentLevel") String intentLevel,
                                                  @PathVariable("confidenceScore") double confidenceScore)
    {
        ResponseEntity<String> responseEntity;
        try {
            knowledgeIndexerService.addRelationship(name,paragraphId,intentLevel,confidenceScore);
            responseEntity = new ResponseEntity<>("Relationship saved sucessfully", HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Error while saving relationship", HttpStatus.BAD_GATEWAY);
        }
        return responseEntity;
    }
}
