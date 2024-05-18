package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.Status
import jakarta.persistence.*

@Entity(name = "LEARNER")
class Learner(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learner_id")
        private var learnerId: Long = 0,

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
        this.role = RoleType.LEARNER
        this.provider = provider
        this.providerId = providerId
        this.loginId = loginId
        this.name = name
    }

    companion object {
        fun createLearner(
                email: String,
                birthDate: String,
                provider: Provider,
                providerId: String,
                loginId: String,
                name: String
        ): Learner {
            return Learner(
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
