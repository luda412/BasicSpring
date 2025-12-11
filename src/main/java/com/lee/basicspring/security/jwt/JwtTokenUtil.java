package com.lee.basicspring.security.jwt;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtil {

    // 32바이트 이상으로 충분히 길게
    private static final String SECRET_KEY =
            "bXktc2VjcmV0LWtleS0xMjMxMjMtbXktc2VjcmV0LWtleS0xMjMxMjM=";

    private static final Key SIGNING_KEY =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // JWT Token 발급
    public static String createToken(String loginId, long expireTimeMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireTimeMs);

        return Jwts.builder()
                .setSubject(loginId)
                .claim("loginId", loginId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Claims에서 loginId 꺼내기
    public static String getLoginId(String token) {
        return extractClaims(token).get("loginId").toString();
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
