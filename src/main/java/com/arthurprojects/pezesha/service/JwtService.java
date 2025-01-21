package com.arthurprojects.pezesha.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
}
