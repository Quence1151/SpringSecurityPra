package com.example.springsecuritypra.service;

import com.example.springsecuritypra.login.MyUserDetail;
import com.example.springsecuritypra.member.Member;
import com.example.springsecuritypra.member.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements UserDetailsService {
    @Autowired
    private final MemberRepositoryImpl memberRepository;

    @Transactional
    public void joinUser(Member member){
        // BCrypt 적용
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.saveUser(member);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
        Member member = memberRepository.findUserById(id);
        return new MyUserDetail(member);
    }
}