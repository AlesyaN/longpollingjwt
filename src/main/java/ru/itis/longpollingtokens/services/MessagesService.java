package ru.itis.longpollingtokens.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.longpollingtokens.dto.MessageDto;
import ru.itis.longpollingtokens.models.Message;
import ru.itis.longpollingtokens.models.User;
import ru.itis.longpollingtokens.repositories.MessageRepository;
import ru.itis.longpollingtokens.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public void save(MessageDto messageDto) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(messageDto.getToken()).getBody();
        Optional<User> userCandidate = userRepository.findFirstByLoginIgnoreCase((String) body.get("login"));
        if (userCandidate.isPresent()) {
            Message message = Message.builder()
                    .text(messageDto.getText())
                    .user(userCandidate.get())
                    .build();
            messageRepository.save(message);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<MessageDto> getAllMessages() {
        List<MessageDto> messageDtos = messageRepository.findAll().stream().map(message -> MessageDto.builder()
                .userName(message.getUser().getLogin())
                .text(message.getText())
                .build()).collect(Collectors.toList());
        return messageDtos;
    }
}
