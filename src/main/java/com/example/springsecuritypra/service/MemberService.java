package com.example.springsecuritypra.service;

import com.example.springsecuritypra.member.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    void joinUser(Member member);
    UserDetails loadUserByUsername(String id);
}
