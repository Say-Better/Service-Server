package io.say.better.storage.mysql.dao.repository

import io.say.better.core.enums.Provider
import java.util.Optional

interface MemberReadRepository<T> {
    fun findByProviderAndLoginId(
        provider: Provider,
        loginId: String,
    ): Optional<T>

    fun findByEmail(email: String): Optional<T>
}
