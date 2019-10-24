package ru.itis.longpollingtokens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.longpollingtokens.forms.UserForm;
import ru.itis.longpollingtokens.services.UserService;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserForm userForm) {
        userService.register(userForm);
        return ResponseEntity.ok().build();
    }
}
