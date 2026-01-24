package com.bambbdd.domain.member.repository;

import com.bambbdd.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByUsername(@NotBlank String username);
}
