package com.example.springsecuritypra.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{
    private final EntityManager em;
    @Override
    public void saveUser(Member member) {
        em.persist(member);
    }
    @Override
    public Member findUserById(String username) {
        TypedQuery<Member> query = em.createQuery("select m from Member as m where m.email = 'aa@aa.com'", Member.class);
        return query.getSingleResult();
    }
}
