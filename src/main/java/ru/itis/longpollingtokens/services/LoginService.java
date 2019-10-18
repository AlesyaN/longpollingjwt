package ru.itis.longpollingtokens.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.longpollingtokens.dto.LoginDto;
import ru.itis.longpollingtokens.models.User;
import ru.itis.longpollingtokens.repositories.UserRepository;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    private String secretKey = "tepaIepaspringjpapropertieshibernatejdbclobnoncontextualcreationjavasosibibucontextualcreationjavasosibibu";

    public String loginByCredentials(LoginDto loginDto) {
        Optional<User> userCandidate = userRepository.findFirstByLoginIgnoreCase(loginDto.getLogin());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
                String token = Jwts.builder()
                        .claim("id", user.getId())
                        .claim("login", user.getLogin())
//                        .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                        .signWith(SignatureAlgorithm.HS512, secretKey)
                        .compact();

                return token;
            }
        } throw new BadCredentialsException("Incorrect login or password");
    }

}
