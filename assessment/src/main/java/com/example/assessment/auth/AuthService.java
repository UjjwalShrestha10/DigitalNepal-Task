package com.example.assessment.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            Authentication authResult = authenticationManager.authenticate(authentication);

            UserDetails userDetails = (UserDetails) authResult.getPrincipal();

            final String jwtToken = jwtService.generateToken(userDetails);

            return new AuthenticationResponse(jwtToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid Username or Password", e);
        }
    }

}
