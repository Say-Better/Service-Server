package io.say.better.storage.mysql.dao.repository;

import io.say.better.core.enums.Provider;

import java.util.Optional;

public interface MemberReadRepository<T> {
	Optional<T> findByProviderAndLoginId(Provider provider, String loginId);
	Optional<T> findByEmail(String email);
}
