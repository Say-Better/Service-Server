package io.say.better.storage.mysql.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.core.enums.Provider;

public interface MemberReadRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByProviderAndLoginId(Provider provider, String loginId);

	Optional<Member> findByEmail(String email);
}
