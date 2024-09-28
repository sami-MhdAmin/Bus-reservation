package com.bus.reservationbus.security;

import com.bus.reservationbus.models.ResrvationApiExcption;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
//utility methods
@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long expiration;

    public String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date expireDate = new Date(new Date().getTime() + expiration);

        return Jwts.builder().setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact(); //compact will return String jwt token
    }

    public String getUserName(String token){ //when we will use this method? when we receive the token from the client when client makes a request to the server and through this token we get the user name
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e){
            throw new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"invalid token");

        }catch (ExpiredJwtException e){
            throw new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"Token expired");
        }catch (UnsupportedJwtException e){
            throw new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"Unsupported token");
        }catch (IllegalArgumentException e){
            throw new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"invalid argument");
        }
    }



    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}