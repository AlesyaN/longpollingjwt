package ru.itis.longpollingtokens.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.longpollingtokens.models.User;
import ru.itis.longpollingtokens.repositories.UserRepository;

import java.util.Optional;

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> authenticationCandidate = userRepository.findUserByLogin(login);
        if (authenticationCandidate.isPresent()) {
            User user = authenticationCandidate.get();
            return new UserDetailsImpl(user);
        } return null;
    }
}
