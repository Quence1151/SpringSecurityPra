package com.example.springsecuritypra.login;

import com.example.springsecuritypra.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetail implements UserDetails {
    private String email;
    private String password;
    private String auth;

    public MyUserDetail(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.auth = "ROLE_" + member.getRole(); // Spring Security는 ROLE_ 형태로 인식
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 계정이 갖고있는 권한 목록 리턴
        return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override // 사용자 계정 만료 판단 여부(true: 만료안됨)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 사용자 계정이 잠겨있는가(true: 잠기지 않음)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 사용자의 비밀번호(암호)가 만료되었는가(true: 만료안됨)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 활성화(사용 가능) 여부 판단(true: 활성화)
    public boolean isEnabled() {
        return true;
    }
}