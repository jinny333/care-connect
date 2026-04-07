package com.careconnect.nursinghome.domain.member.repository;

import com.careconnect.nursinghome.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원을 찾는 기능 (로그인이나 중복 가입 체크 때 쓸 거야!)
    Optional<Member> findByEmail(String email);
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}