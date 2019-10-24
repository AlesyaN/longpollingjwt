package ru.itis.longpollingtokens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.longpollingtokens.dto.LoginDto;
import ru.itis.longpollingtokens.services.LoginService;

@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/login-token")
    @CrossOrigin
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> loginByToken() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login-cred")
    @CrossOrigin
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> loginByCredentials(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(service.loginByCredentials(loginDto));
    }


}
