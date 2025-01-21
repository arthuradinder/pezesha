package com.arthurprojects.pezesha.controller;

import com.arthurprojects.pezesha.entity.AuthenticationRequest;
import com.arthurprojects.pezesha.entity.AuthenticationResponse;
import com.arthurprojects.pezesha.entity.RegisterRequest;
import com.arthurprojects.pezesha.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related endpoints such as user registration,
 * login (authentication), and token refreshing.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint for user registration. The user provides their details in the request,
     * and an authentication response containing access and refresh tokens is returned.
     *
     * @param request The user registration details.
     * @return A ResponseEntity containing the authentication response (access and refresh tokens).
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint for user authentication (login). The user provides their email and password in the request,
     * and an authentication response containing access and refresh tokens is returned.
     *
     * @param request The authentication request containing user email and password.
     * @return A ResponseEntity containing the authentication response (access and refresh tokens).
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint to refresh the JWT access token using the provided refresh token. The refresh token is sent in the
     * Authorization header as a Bearer token. A new access token and the same refresh token are returned in the response.
     *
     * @param request  The HTTP request containing the refresh token in the Authorization header.
     * @param response The HTTP response in which the new access token is written.
     * @throws Exception if the refresh token is invalid or cannot be processed.
     */
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authenticationService.refreshToken(request, response);
    }
}
