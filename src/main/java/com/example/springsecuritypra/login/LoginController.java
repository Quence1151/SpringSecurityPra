package com.example.springsecuritypra.login;

import com.example.springsecuritypra.jwt.JwtTokenProvider;
import com.example.springsecuritypra.member.Member;
import com.example.springsecuritypra.service.MemberServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberServiceImpl service;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(Member member) {
        member.setRole("USER");
        service.joinUser(member);
        log.info(member.getEmail());
        return "redirect:/login";
    }

    @GetMapping("/")
    public String userAccess(Model model, Authentication authentication) {
        MyUserDetail userDetail = (MyUserDetail)authentication.getPrincipal();
        log.info(userDetail.getUsername());
        model.addAttribute("info", jwtTokenProvider.createToken(userDetail.getUsername(), userDetail.getAuthorities()));
        return "list";
    }
}