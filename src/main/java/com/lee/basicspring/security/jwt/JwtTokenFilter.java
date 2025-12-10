package com.lee.basicspring.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberServiceImpl memberServiceImpl;
    // private final String secretKey; // JwtTokenUtil 내부에서 키를 관리하므로 더 이상 필요 없음

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Implement JWT token filtering logic here
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Header의 Authorization 값이 비어 있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null){
            filterChain.doFilter(request, response);
            return;
        }

       //Header의 Authorization 값이 'Bearer'로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
       //전송 받은 값에서 "Bearer" 뒷부분 (Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];
        
        //전송 받은 Jwt Token이 만료되었스면 => 다음 필터 진행 (인증 x)
        if(JwtTokenUtil.isExpired(token)){
            filterChain.doFilter(request, response);
            return;
        }

        //Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token);

        //추출한 loginId로 User 찾아오기
        Member loginMember = memberServiceImpl.getLoginMemberByLoginId(loginId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginMember.getLoginId(), // 또는 loginMember 자체를 principal로 써도 됨
                        null,
                        List.of(new SimpleGrantedAuthority(loginMember.getRole().name()))
                );

        //권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);



    }
}