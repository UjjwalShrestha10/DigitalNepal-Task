package com.example.assessment.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
