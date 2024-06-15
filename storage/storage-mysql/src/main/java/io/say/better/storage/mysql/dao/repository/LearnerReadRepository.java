package io.say.better.storage.mysql.dao.repository;

import io.say.better.core.common.Provider;
import io.say.better.storage.mysql.domain.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearnerReadRepository extends JpaRepository<Learner, Long>, MemberReadRepository<Learner> {

	Optional<Learner> findByProviderAndLoginId(Provider provider, String loginId);

	Optional<Learner> findByEmail(String email);
}
