package com.product.commonservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class CommonService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new RuntimeException("No current request found");
        }
        return attrs.getRequest();
    }

    // Extract claims from JWT token
    private Claims getClaimsFromToken() {
        HttpServletRequest request = getCurrentHttpRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Now these methods need NO arguments
    public String getMobile() {
        return getClaimsFromToken().getSubject();
    }

    public String getRole() {
        return (String) getClaimsFromToken().get("roles");
    }

    public Long getUserId() {
        Object id = getClaimsFromToken().get("id");
        return id != null ? Long.parseLong(id.toString()) : null;
    }

    public boolean isTokenExpired() {
        return getClaimsFromToken().getExpiration().before(new java.util.Date());
    }
}