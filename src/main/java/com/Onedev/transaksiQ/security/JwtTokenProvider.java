package com.Onedev.transaksiQ.security;

import com.Onedev.transaksiQ.exception.TransactionApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    public Key key(){
        return Keys
                .hmacShaKeyFor(
                        Decoders.BASE64.decode(jwtSecret)
                );
    }

    //get username from JWT token
    public String getEmail(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token){

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;

        }catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | MalformedJwtException | ExpiredJwtException ex){
            throw new TransactionApiException(HttpStatus.UNAUTHORIZED, "Token tidak valid atau kadaluwarsa", 108);
        }
    }
}
