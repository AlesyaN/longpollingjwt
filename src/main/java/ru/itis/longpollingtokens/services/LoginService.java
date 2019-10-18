package ru.itis.longpollingtokens.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.longpollingtokens.dto.LoginDto;
import ru.itis.longpollingtokens.dto.TokenDto;
import ru.itis.longpollingtokens.models.User;
import ru.itis.longpollingtokens.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";

    public TokenDto loginByCredentials(LoginDto loginDto) {
        Optional<User> userCandidate = userRepository.findFirstByLoginIgnoreCase(loginDto.getLogin());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
                String acsessToken = Jwts.builder()
                        .claim("id", user.getId())
                        .claim("login", user.getLogin())
                        .signWith(SignatureAlgorithm.HS512, secretKey)
                        .compact();

                String refreshToken = Jwts.builder()
                        .claim("id", user.getId())
                        .signWith(SignatureAlgorithm.HS512, secretKey)
                        .compact();

                LocalDateTime accessExpiresIn = LocalDateTime.now().plusDays(2);

                TokenDto token = TokenDto.builder()
                        .accessToken(acsessToken)
                        .refreshToken(refreshToken)
                        .accessExpiresIn(accessExpiresIn.toEpochSecond(ZoneOffset.UTC))
                        .build();
                return token;
            }
        } throw new BadCredentialsException("Incorrect login or password");
    }

    public TokenDto refreshToken(String refreshToken) {

    }
}
