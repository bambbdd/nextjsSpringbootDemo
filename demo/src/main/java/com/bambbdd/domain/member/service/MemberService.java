package com.bambbdd.domain.member.service;

import com.bambbdd.domain.member.entity.Member;
import com.bambbdd.domain.member.repository.MemberRepository;
import com.bambbdd.global.jwt.JwtProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public void authAndMakeTokens(@NotBlank String username, @NotBlank String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        
        // 시간 설정 및 토큰 생성
        String accessToken = jwtProvider.genToken(member, 60 * 60 * 5);

        System.out.println("accessToken: " + accessToken);
    }
}
