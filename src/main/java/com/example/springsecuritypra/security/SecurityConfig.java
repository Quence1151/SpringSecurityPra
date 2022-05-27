package com.example.springsecuritypra.security;

import com.example.springsecuritypra.jwt.JwtSecurityConfig;
import com.example.springsecuritypra.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springsecuritypra.jwt.JwtTokenProvider;


@EnableWebSecurity
// WebSecurityConfigurerAdapter : 스프링 시큐리티의 웹 보안 기능의 초기화 및 설정 담당
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberServiceImpl memberService;
    private JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 규칙 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/signup").anonymous().and()
        .formLogin().and()
        .cors().and()
        .csrf().disable()//로그인 창
        ;
    }

    // 로그인 인증 처리 메소드
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
    }
}