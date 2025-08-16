package com.mahammad.chatapp.controllers;

import com.mahammad.chatapp.model.Message;
import com.mahammad.chatapp.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RealtimeChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messages")
    public void receiveMessage(@Payload Message message) {
        for (User user : message.getChat().getUsers()) {
            final String destination = "/topic/" + user.getId();
            messagingTemplate.convertAndSend(destination, message);
        }
    }

}
