package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity(name = "MEMBER")
class Member(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name = "member_id", nullable = false)
    val memberId: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: Status = Status.ACTIVE,
    @Column(name = "email", nullable = false, length = 100)
    val email: String = "",
    @Column(name = "birth_date")
    val birthDate: String = "",
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    var role: RoleType = RoleType.NONE,
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    val provider: Provider? = null,
    @Column(name = "provider_id", nullable = false)
    val providerId: String? = null,
    @Column(name = "login_id", nullable = false)
    val loginId: String? = null,
    @Column(name = "name", nullable = false, length = 100)
    val name: String? = null,
) : BaseTimeEntity() {
    fun onEducatorLearner() {
        this.role = RoleType.EDUCATOR_LEARNER
    }

    companion object {
        fun createMember(
            email: String,
            birthDate: String,
            role: RoleType,
            provider: Provider,
            providerId: String,
            loginId: String,
            name: String,
        ): Member {
            return Member(
                email = email,
                birthDate = birthDate,
                role = role,
                provider = provider,
                providerId = providerId,
                loginId = loginId,
                name = name,
            )
        }

    }
}
