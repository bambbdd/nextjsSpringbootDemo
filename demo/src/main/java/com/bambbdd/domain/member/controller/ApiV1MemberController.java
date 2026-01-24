package com.bambbdd.domain.member.controller;

import com.bambbdd.domain.member.entity.Member;
import com.bambbdd.domain.member.service.MemberService;
import com.bambbdd.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;

    @Getter
    public static class LoginRequestBody {
        @NotBlank
        public String username;
        @NotBlank
        public String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class LoginResponseBody {
            private Member member;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody loginRequestBody) {
        // username, password => accessToken
        memberService.authAndMakeTokens(loginRequestBody.getUsername(), loginRequestBody.getPassword());


        return RsData.of("ok", "ok");
    }
}
