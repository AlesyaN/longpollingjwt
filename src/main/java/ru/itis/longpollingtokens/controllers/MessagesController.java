package ru.itis.longpollingtokens.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.longpollingtokens.dto.MessageDto;
import ru.itis.longpollingtokens.forms.MessageForm;
import ru.itis.longpollingtokens.security.authentication.JwtAuthentication;
import ru.itis.longpollingtokens.security.details.UserDetailsImpl;
import ru.itis.longpollingtokens.services.MessagesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
    private final Map<String, List<MessageDto>> messages = new HashMap<>();

    @Autowired
    MessagesService messagesService;

    @CrossOrigin
    @PreAuthorize("permitAll()")
    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageForm messageForm, @RequestHeader("AUTH") String token) {
        if (!messages.containsKey(token)) {
            messages.put(token, new ArrayList<>());
        }
        for (List<MessageDto> pageMessages : messages.values()) {
            synchronized (pageMessages) {
                MessageDto messageDto = MessageDto.builder()
                        .token(token)
                        .text(messageForm.getText())
                        .build();
                if (messageForm.getText() != null) {
                    messagesService.save(messageDto);
                    pageMessages.add(messageDto);
                    pageMessages.notifyAll();
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PreAuthorize("permitAll()")
    @SneakyThrows
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestHeader("AUTH") String token) {
        synchronized (messages.get(token)) {
            if (messages.get(token).isEmpty()) {
                messages.get(token).wait();
            }
            List<MessageDto> response = new ArrayList<>(messages.get(token));
            messages.get(token).clear();
            return ResponseEntity.ok(response);
        }
    }

    @SneakyThrows
    @CrossOrigin
    @PreAuthorize("permitAll()")
    @GetMapping("/get-all-messages")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        return ResponseEntity.ok(messagesService.getAllMessages());
    }

}
