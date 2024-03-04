package com.saybetter.domain.member.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.common.constant.Provider;

public interface MemberReadRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByProviderAndLoginId(Provider provider, String loginId);

	Optional<Member> findByEmail(String email);
}
