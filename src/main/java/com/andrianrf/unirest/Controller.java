package com.andrianrf.unirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class Controller {

    @PostMapping()
    public ResponseEntity<Object> root(@RequestBody Model model) {
        log.info("model : "+model);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseJson = null;
        String responseString = null;
        try {
//            {
//                "requestBody": "{\"title\": \"I am in love with someone.\", \"userId\": \"5\"}",
//                    "requestUrl": "https://dummyjson.com/posts/add"
//            }
            Map<String, Object> requestBodyJson = objectMapper.readValue(model.getRequestBody(), Map.class);

            Unirest.config().verifySsl(false);
            Unirest.config().proxy("172.17.103.9", 3128);
            HttpResponse<String> response = Unirest.post(model.getRequestUrl())
                .header("Content-Type", "application/json")
                .body(requestBodyJson)
                .asString();

//            responseJson = objectMapper.readValue(response, Map.class);
        log.info("response => "+response);

        responseString = "httpStatus: "+response.getStatus()+";responseBody: "+response.getBody();

        }
        catch (JsonProcessingException e){
            log.info(e.getMessage());
        }

        return ResponseHandler.generateResponse(HttpStatus.OK, responseString, "success");
    }

}
