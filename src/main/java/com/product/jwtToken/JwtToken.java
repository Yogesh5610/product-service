package com.product.jwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class JwtToken {

    private final String secretKey = "mysecretkey12345678901234567890"; // Same as used for signing

    // Get current HttpServletRequest from context
    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new RuntimeException("No current HTTP request");
        }
        return attrs.getRequest();
    }

    // Extract token from Authorization header
    private String extractToken() {
        HttpServletRequest request = getCurrentHttpRequest();
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    // Extract claims from token
    private Claims getClaims() {
        String token = extractToken();
        if (token == null) {
            throw new RuntimeException("JWT Token is missing");
        }
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId() {
        Claims claims = getClaims();
        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof Integer) {
            return ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            return (Long) userIdObj;
        }
        throw new RuntimeException("Invalid userId type in token");
    }

    public String getUserRole() {
        Claims claims = getClaims();
        return claims.get("role", String.class);
    }
}
