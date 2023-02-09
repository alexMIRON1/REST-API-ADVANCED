package com.epam.esm.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * This interface {@code JwtService} is used to manipulate with jwt token.
 * @author Oleksandr Myronenko
 */
public interface JwtService {
    /**
     * This method is used to get user's name from jwt token.
     * @param jwtToken jwt token
     * @return user's name
     */
    String extractUserName(String jwtToken);

    /**
     * This method is used to get claim.
     * @param jwtToken jwt token
     * @param claimsResolver claim resolver
     * @return claim
     * @param <T> type of claim
     */
    <T> T extractClaim(String jwtToken, Function<Claims,T> claimsResolver);

    /**
     * This method is used to generate token without extra claims.
     * @param userDetails user's details
     * @return token
     */
    String generateToken(UserDetails userDetails);
    /**
     * This method is used to generate token with extra claims.
     * @param extraClaims extra claims
     * @param userDetails user's details
     * @return token
     */
    String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
    /**
     * This method is used to check that token is valid.
     * @param jwtToken  jwt token
     * @param userDetails user details
     * @return boolean value
     */
     boolean isTokenValid(String jwtToken, UserDetails userDetails);

}
