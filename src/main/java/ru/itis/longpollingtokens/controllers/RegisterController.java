package ru.itis.longpollingtokens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.longpollingtokens.forms.UserForm;
import ru.itis.longpollingtokens.services.UserService;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/chat";
        }
        return "register";
    }

    @PostMapping
    public String register(UserForm userForm) {
        userService.register(userForm);
        return "redirect:/login";
    }
}
