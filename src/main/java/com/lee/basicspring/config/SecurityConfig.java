package com.lee.basicspring.config;

import com.lee.basicspring.data.entity.type.MemberRole;
import com.lee.basicspring.security.jwt.JwtTokenFilter;
import com.lee.basicspring.service.MemberServiceImpl;
import com.lee.basicspring.service.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final MemberServiceImpl memberServiceImpl;
        private final PrincipalOAuth2UserService principalOAuth2UserService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션을 사용하지 않는 JWT 기반 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)
                )

                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(
                        new JwtTokenFilter(memberServiceImpl),
                        UsernamePasswordAuthenticationFilter.class
                )

                // 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 폼 로그인 + OAuth2 영역
                        .requestMatchers("/security-login/info").authenticated()
                        .requestMatchers("/security-login/admin/**").hasAuthority(MemberRole.ADMIN.name())
                        // JWT 영역
                        .requestMatchers("/jwt-login/info").authenticated()
                        .requestMatchers("/jwt-login/admin/**").hasAuthority(MemberRole.ADMIN.name())
                        // 나머지는 모두 허용
                        .anyRequest().permitAll()
                )

                // Form Login 설정
                .formLogin(login -> login
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginPage("/security-login/login")
                        .defaultSuccessUrl("/security-login")
                        .failureUrl("/security-login/login")
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/security-login/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/security-login/login")
                        .defaultSuccessUrl("/security-login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOAuth2UserService)
                        )
                );

        return http.build();
        }
}
