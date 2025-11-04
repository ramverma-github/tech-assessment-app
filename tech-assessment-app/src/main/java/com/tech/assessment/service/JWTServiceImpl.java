package com.tech.assessment.service;

import com.tech.assessment.model.User;
import com.tech.assessment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Override
    public String extractUserName(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(getJwtSecretKey()).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception exp) {
            return null;
        }
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getJwtSecretKey()).build().parseClaimsJws(token);
            String username = claims.getBody().getSubject();
            return username.equals(userDetails.getUsername()) && !isTokenExpired(claims.getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getJwtSecretKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getJwtSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
