package ru.itis.longpollingtokens.controllers;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.longpollingtokens.dto.MessageDto;
import ru.itis.longpollingtokens.security.authentication.JwtAuthentication;
import ru.itis.longpollingtokens.security.details.UserDetailsImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
    private final Map<String, List<MessageDto>> messages = new HashMap<>();

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Object> receiveMessage(@RequestParam("message")String text, Authentication authentication) {
        String tokenValue = ((UserDetailsImpl) authentication.getPrincipal()).getCurrentToken();
        if (!messages.containsKey(tokenValue)) {
            messages.put(tokenValue, new ArrayList<>());
        }
        for (List<MessageDto> pageMessages : messages.values()) {
            synchronized (pageMessages) {
                MessageDto message = MessageDto.builder()
                        .text(text)
                        .token(tokenValue)
                        .build();
                pageMessages.add(message);
                pageMessages.notifyAll();
            }
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(Authentication authentication) {
        String tokenValue = ((UserDetailsImpl) authentication.getPrincipal()).getCurrentToken();
        synchronized (messages.get(tokenValue)) {
            if (messages.get(tokenValue).isEmpty()) {
                messages.get(tokenValue).wait();
            }
            List<MessageDto> response = new ArrayList<>(messages.get(tokenValue));
            messages.get(tokenValue).clear();
            return ResponseEntity.ok(response);
        }
    }

}
