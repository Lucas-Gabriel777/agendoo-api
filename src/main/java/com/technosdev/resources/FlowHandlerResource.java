package com.technosdev.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technosdev.enums.MessageStatusEnum;
import com.technosdev.flow.entities.ConfigFlow;
import com.technosdev.services.FlowHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/webhook")
public class FlowHandlerResource {
    private static final Logger LOGGER = Logger.getLogger(FlowHandlerResource.class.getName());

    @Autowired
    private FlowHandlerService flowHandlerService;

    @Autowired
    private ConfigFlow configFlow;

    @GetMapping
    public ResponseEntity<String> webhookInitially(
            @RequestParam("hub.mode") String hubMode,
            @RequestParam("hub.challenge") String hubChallenge,
            @RequestParam("hub.verify_token") String hubVerifyToken
    ) {

        if (hubMode.equals("subscribe") && hubVerifyToken.equals(configFlow.getWEBHOOK_VERIFY_TOKEN())) {
            return ResponseEntity.ok().body(hubChallenge);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<String> processIncomingData(@RequestBody String payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(payload);
        LOGGER.info(payload);
        String response = "";

        try {
            JsonNode jsonNode = objectMapper.readTree(payload);

            if (jsonNode.has("object") && jsonNode.get("object").asText().equals("whatsapp_business_account")) {
                response = flowHandlerService.receivedMessage(jsonNode);
            } else if (jsonNode.has("encrypted_flow_data") && jsonNode.has("encrypted_aes_key") && jsonNode.has("initial_vector")) {
                response = flowHandlerService.encryptedStream(jsonNode);
            } else {
                System.out.println("Unknow body: " + jsonNode.toString());
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(MessageStatusEnum.ERROR_MESSAGE);
        }
    }
}
