package ru.itis.longpollingtokens.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.longpollingtokens.dto.TokenDto;
import ru.itis.longpollingtokens.models.User;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private User user;
    private TokenDto currentToken;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setCurrentToken(TokenDto currentToken) {
        this.currentToken = currentToken;
    }

    public User getUser() {
        return user;
    }

    public TokenDto getCurrentToken() {
        return currentToken;
    }

}
