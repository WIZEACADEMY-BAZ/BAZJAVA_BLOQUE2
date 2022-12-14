package com.wizeline.baz.LearningSpring.controller;

import com.wizeline.baz.LearningSpring.patron.comportamiento.Chat;
import com.wizeline.baz.LearningSpring.patron.comportamiento.ChatPublisher;
import com.wizeline.baz.LearningSpring.patron.comportamiento.ReadMessage;
import com.wizeline.baz.LearningSpring.patron.comportamiento.SendMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Tag(name = "Observer Controller", description = "Servicio chat")
@RequestMapping("/api2")
@RestController
public class ObserverController {

    private static final Logger LOGGER = Logger.getLogger(ObserverController.class.getName());


    @GetMapping("/chat/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestParam String messagge) {

        SendMessage sendMessage = new SendMessage();
        ReadMessage readMessage = new ReadMessage();
        ChatPublisher publisher = new ChatPublisher();
        publisher.attach(sendMessage);
        publisher.attach(readMessage);
        publisher.notifyMessage(new Chat(messagge));
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
    @GetMapping("/chat/readMessage")
    public ResponseEntity<String> readMessage(@RequestParam String messagge) {

        SendMessage sendMessage = new SendMessage();
        ReadMessage readMessage = new ReadMessage();
        ChatPublisher publisher = new ChatPublisher();
        publisher.attach(sendMessage);
        publisher.attach(readMessage);
        publisher.notifyMessage(new Chat("LEIDO"));
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
