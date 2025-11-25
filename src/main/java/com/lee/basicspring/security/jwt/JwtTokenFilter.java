package com.lee.basicspring.security.jwt;

import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberServiceImpl memberServiceImpl;
    private final String secretKey;

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
        if(JwtTokenUtil.isExpired(token, secretKey)){
            filterChain.doFilter(request, response);
            return;
        }

        //Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token, secretKey);

        //추출한 loginId로 User 찾아오기
        Member loginMember = memberServiceImpl.getLoginMemberByLoginId(loginId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginMember.getLoginId(), null, List.of(new SimpleGrantedAuthority(loginMember.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);



    }
}