package io.say.better.storage.mysql.dao.repository;

import io.say.better.core.enums.Provider;
import io.say.better.storage.mysql.domain.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducatorReadRepository extends JpaRepository<Educator, Long>, MemberReadRepository<Educator> {

	Optional<Educator> findByProviderAndLoginId(Provider provider, String loginId);

	Optional<Educator> findByEmail(String email);
}
