package com.careconnect.nursinghome.domain.member.service;

import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.entity.Role;
import com.careconnect.nursinghome.domain.member.repository.MemberRepository;
import com.careconnect.nursinghome.global.auth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 1. 어느 서비스인지 구분 (naver, kakao 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 2. 네이버는 유저 정보가 "response" 안에 담겨 있어서 꺼내줘야 함
        Map<String, Object> attributes = oAuth2User.getAttributes();
        if ("naver".equals(provider)) {
            attributes = (Map<String, Object>) attributes.get("response");
        }

        // 3. 네이버에서 주는 고유 ID와 이메일 등 추출
        String providerId = (String) attributes.get("id");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // 4. 이미 가입된 소셜 유저인지 확인 후, 없으면 빌더로 새로 생성
        Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(email)
                            .name(name)
                            .provider(provider)
                            .providerId(providerId)
                            .role(Role.CUSTOMER) // 기본 역할 설정
                            .isDeleted(false)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 5. Spring Security 세션에 저장할 객체 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getRole().name())),
                attributes,
                "id",      // 네이버 응답 식별자
                member.getId(),   // ⭐ DB에서 생성된 진짜 ID 전달!
                member.getRole().name()
        );
    }
}
