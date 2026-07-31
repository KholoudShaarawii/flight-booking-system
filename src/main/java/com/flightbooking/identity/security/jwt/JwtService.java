package com.flightbooking.identity.security.jwt;

import com.flightbooking.identity.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService { //Token operations

    private static final String ROLE_CLAIM = "role";

    private final String secretKey;

    public JwtService(
            @Value("${security.jwt.secret}")
            String secretKey
    ) {
        this.secretKey = secretKey;
    }

    public String generateToken(User user) {

        Map<String, Object> claims = Map.of( ROLE_CLAIM, user.getRole().name());

        return createToken(claims, user.getEmail());
    }

    private String createToken( Map<String, Object> claims, String subject ) {

        Date issuedAt = new Date();


        Date expiration = new Date(issuedAt.getTime() + 30L * 60 * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateAndExtractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey.trim());

        return Keys.hmacShaKeyFor(keyBytes);
    }
}