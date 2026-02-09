package com.example.jwtdemo.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String Secret_Key;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Secret_Key.getBytes());
    }




    public String generateToken(String username) {
        //claims are for the  sending the  payload for eg username + mail ,here it is empty
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);


    }

    //here username -> subject (subject is thing that will be unique, and we take username as subject)
    private String createToken(Map<String, Object> claims, String username) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
                    .signWith(getSigningKey())
                    .compact();



    }

    //extract username
    public String extractUsername(String token){
            return extractAllClaim(token).getSubject();
    }

    // token expired
    public boolean isTokenExpired(String token){
        return extractAllClaim(token).getExpiration().before(new Date());
    }

    //valid toke
    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }






    //extract all claims
    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
