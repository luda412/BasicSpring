package com.lee.basicspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lee.basicspring.data.entity.type.MemberRole;
import com.lee.basicspring.security.jwt.JwtTokenFilter;
import com.lee.basicspring.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberServiceImpl memberServiceImpl;
//     private static String scretKey = "my-secret-key-123123";

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/security-login/info").authenticated()
//                        .requestMatchers("/security-login/admin/**").hasAuthority(MemberRole.ADMIN.name())
//                        .anyRequest().permitAll()
//                )
//                .formLogin(login -> login
//                        .usernameParameter("loginId")
//                        .passwordParameter("password")
//                        .loginPage("/security-login/login")
//                        .defaultSuccessUrl("/security-login")
//                        .failureUrl("/security-login/login")
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/security-login/logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic(http -> http.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)
                )
                .addFilterBefore(
                        new JwtTokenFilter(memberServiceImpl),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/jwt-login/info").authenticated()
                        .requestMatchers("/jwt-login/admin/**").hasAuthority(MemberRole.ADMIN.name())
                        .anyRequest().permitAll()
                )
                .build();
    }

}
