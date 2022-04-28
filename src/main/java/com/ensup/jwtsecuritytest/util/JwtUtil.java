package com.ensup.jwtsecuritytest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
@Slf4j
public class JwtUtil {

    private Long oneHour = Long.valueOf(1000 * 60 * 60 * 10);
    private String secret = "toto";

    public Boolean validateToken(String token, UserDetails userDetails) throws Exception{
        final String username = extractUsername(token);
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public <T>  T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception{
        Claims claims = null ;
        try {
            claims = extractAllClaims(token);
        } catch (SignatureException e){
            throw new RuntimeException("Signature JWT token non valide");
        }

        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    // PRIVATE METHODS
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + oneHour))
                .signWith(SignatureAlgorithm.HS256,secret).compact();

    }


    private Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) throws Exception{
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

}
