package com.example.assessment.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "/auth/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = service.login(authenticationRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationResponse.getToken());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
