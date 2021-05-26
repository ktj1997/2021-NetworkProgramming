package com.ssu.network.chat.core.security;

import com.ssu.network.chat.api.model.user.UserRole;
import com.ssu.network.chat.core.security.exception.NotBearerTokenException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${application.jwt.secret}")
    String jwtSecret;

    @Value("${application.access.expire}")
    Long accessTokenExpire;

    @Value("${application.refresh.expire}")
    Long refreshTokenExpire;


    public String checkingAndParsingBearer(String token) {
        if (token != null) {
            if (token.startsWith("Bearer "))
                return token.replace("Bearer ", "");
            throw new NotBearerTokenException();
        }
        return null;
    }

    public String generateAccessToken(Long userId,String userName,UserRole role) {
        return generateToken(userId,userName, role,accessTokenExpire);
    }

    public String generateRefreshToken(Long userId,String userName,UserRole role) {
        return generateToken(userId, userName ,role, refreshTokenExpire);
    }

    public String generateToken(Long userId,String userName, UserRole role, Long expiredAt) {
        Date expire = new Date(new Date().getTime() + expiredAt);

        return Jwts.builder()
                .claim("id", userId)
                .claim("userName",userName)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public String getUserNameFromClaims(Claims claims){
        return claims.get("userName",String.class);
    }
    public Long getUserIdFromClaims(Claims claims) {
        return claims.get("id", Long.class);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public GrantedAuthority getAuthoritiesFromClaims(Claims claims) {
        String role = claims.get("roles", String.class);
        return new SimpleGrantedAuthority(role);
    }

    public Authentication getAuthentication(Claims claims) {
        return new UsernamePasswordAuthenticationToken(getUserIdFromClaims(claims), "", List.of(getAuthoritiesFromClaims(claims)));
    }
}
