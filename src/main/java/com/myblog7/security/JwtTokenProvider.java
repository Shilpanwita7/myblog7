package com.myblog7.security;



import com.myblog7.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milisecond}")
    private int jwtExpirationInMs;

//    generate token
    public String generateToken(Authentication authentication){
        String username= authentication.getName();
        Date currentDate= new Date();
        Date expireDate= new Date(currentDate.getTime() + jwtExpirationInMs); // currentDate and time we got here from that time expiration time will start

        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //create a token HS512 algorithm and secure that with secret key jwtSecret
                .compact();
        return token;
    }

//    get username from the token
    public  String getUsenameFromJWT(String token){
        Claims claims= Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

//    validate jwt token
    public  boolean validateToken(String token) throws BlogAPIException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid jwt signature");
        }catch (MalformedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid jwt token");
        }catch (ExpiredJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "expired jwt token");
        }catch (UnsupportedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "unsupported jwt token");
        }catch (IllegalArgumentException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "jwt claims string is empty");
        }
    }
}
