package com.stackroute.listener;

import com.stackroute.domain.WebDocument;
import com.stackroute.service.WebAnalyticService;
import com.stackroute.service.WebDocumentProviderService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class KafkaConsumer {
    private WebAnalyticService webAnalyticService;
    private WebDocumentProviderService webDocumentProviderService;
    private KafkaProducer kafkaProducer;

    @Autowired
    public KafkaConsumer(WebDocumentProviderService webDocumentProviderService,
                         WebAnalyticService webAnalyticService,
                         KafkaProducer kafkaProducer) {
        this.webAnalyticService = webAnalyticService;
        this.webDocumentProviderService = webDocumentProviderService;
        this.kafkaProducer=kafkaProducer;
    }

    // It listens to topic name "WebContentExtractor"
    @KafkaListener(topics = "WebContentExtractor", groupId = "group_id")
    public void consume(String message) throws IOException {
        JSONObject object = (JSONObject) JSONValue.parse(message);
        WebDocument webDocument = new WebDocument();
        if (object.get("title")!=null){
            webDocument.setTitle(object.get("title").toString());
        }else {
            webDocument.setTitle("");
        }
        if (object.get("description")!=null){
            webDocument.setDescription(object.get("description").toString());
        }else {
            webDocument.setDescription("");
        }
        if (object.get("keywords")!=null){
            webDocument.setKeywords(object.get("keywords").toString());
        }else {
            webDocument.setKeywords("");
        }
        if (object.get("imageCount")!=null){
            webDocument.setImageCount(Integer.parseInt(object.get("imageCount").toString()));
        }else {
            webDocument.setImageCount(0);
        }
        if (object.get("codePercentage")!=null){
            webDocument.setCodePercentage(Float.parseFloat(object.get("codePercentage").toString()));
        }else {
            webDocument.setCodePercentage(0);
        }
        if (object.get("id")!=null){
            webDocument.setId(object.get("id").toString());
        }else {
            webDocument.setId("");
        }
        if (object.get("link")!=null){
            webDocument.setLink(object.get("link").toString());
        }else {
            webDocument.setLink("");
        }
        if (object.get("conceptName")!=null){
            webDocument.setConceptName(object.get("conceptName").toString());
        }else {
            webDocument.setConceptName("");
        }
        if (object.get("domain")!=null){
            webDocument.setDomain(object.get("domain").toString());
        }else {
            webDocument.setDomain("");
        }
        // these method are similar to the methods present in controller
        webDocumentProviderService.setWebDocument(webDocument);

        // After analysis we call the postservice to post in kafka message bus
        kafkaProducer.postservice();
    }
}