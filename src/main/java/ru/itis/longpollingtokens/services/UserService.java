package ru.itis.longpollingtokens.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.longpollingtokens.forms.UserForm;
import ru.itis.longpollingtokens.models.User;
import ru.itis.longpollingtokens.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UserForm userForm) {
        userRepository.save(User.builder()
                .login(userForm.getLogin())
                .passwordHash(passwordEncoder.encode(userForm.getPassword()))
                .build());
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

}
