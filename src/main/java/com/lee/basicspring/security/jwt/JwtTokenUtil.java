package com.lee.basicspring.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    
    //JWT Token 발급
    public static String createToken(String loginId, String key, long expireTimeMs){
        //Claim = Jwt Token에 들어갈 정보
        //Claim에 loginId를 넣어 줌으로써 나중에 longinId를 꺼낼 수 있도록 함.
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims) // 이때 위에서 put해준 loginId가 들어감.
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    //Claims에서 loginId 꺼내기
    public static String getLoginId(String token, String secretKey){
        return extractClaims(token, secretKey).get("loginId").toString();
    }

    //발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey){
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check .before 메서드는 이 날짜가 인자로 주어진 날짜보다 과거인지를 비교한다. 즉 false이면 아직 유효, true이면 만료됨
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token, String secretKey){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
