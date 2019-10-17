package ru.itis.longpollingtokens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.longpollingtokens.dto.LoginDto;
import ru.itis.longpollingtokens.services.LoginService;

@RestController
public class RestApiController {

    @Autowired
    private LoginService service;

    @GetMapping("/api/login-token")
    public ResponseEntity<String> loginByToken() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/login-cred")
    public ResponseEntity<String> loginByCredentials(LoginDto loginDto) {
        return ResponseEntity.ok(service.loginByCredentials(loginDto));
    }


}
