package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.Status
import jakarta.persistence.*

@Entity(name = "EDUCATOR")
class Educator(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "educator_id")
        private val educatorId: Long = 0,

        email: String,
        birthDate: String,
        provider: Provider,
        providerId: String,
        loginId: String,
        name: String
) : Member() {


    init {
        this.status = Status.ACTIVE
        this.email = email
        this.birthDate = birthDate
        this.role = RoleType.EDUCATOR
        this.provider = provider
        this.providerId = providerId
        this.loginId = loginId
        this.name = name
    }

    companion object {
        fun createEducator(
                email: String,
                birthDate: String,
                provider: Provider,
                providerId: String,
                loginId: String,
                name: String
        ): Educator {
            return Educator(
                    email = email,
                    birthDate = birthDate,
                    provider = provider,
                    providerId = providerId,
                    loginId = loginId,
                    name = name
            )
        }
    }
}
