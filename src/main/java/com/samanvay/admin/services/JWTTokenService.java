package com.samanvay.admin.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.samanvay.admin.config.GlobalLiterals;
import com.samanvay.admin.entity.DTO.AuthDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService {
  private String JWT_SECRET = "";

  public JWTTokenService () {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");

      SecretKey secretKey = keyGen.generateKey();

      JWT_SECRET = Base64.getEncoder().encodeToString(secretKey.getEncoded());

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private Key getKey() {
    byte[] key = Base64.getDecoder().decode(JWT_SECRET);

    return Keys.hmacShaKeyFor(key);
  }

  public AuthDTO generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();

    AuthDTO authPayload = AuthDTO.builder()
      .username(username)
      .token(
        Jwts
          .builder() 
          .claims()
          .add(claims)
          .subject(username)
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis() + GlobalLiterals.JWT_EXPIRATION_TIME))
          .and()
          .signWith(getKey())
          .compact()
      )
      .message(username + " is authenticated")
      .build();

    return authPayload;
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    
    return claimsResolver.apply(claims);
  }
  
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
      .verifyWith((SecretKey) getKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
    
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
