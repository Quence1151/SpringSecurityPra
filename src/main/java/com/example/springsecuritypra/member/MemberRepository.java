package com.example.springsecuritypra.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository {
    void saveUser(Member member); //save
    Member findUserById(String  memberId); //findById
}
