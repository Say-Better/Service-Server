package io.say.better.storage.mysql.domain.entity

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.core.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class Member : BaseTimeEntity() {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: Status? = null

    @Column(name = "email", nullable = false, length = 100)
    var email: String? = null

    @Column(name = "birth_date")
    var birthDate: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    var role: RoleType? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    var provider: Provider? = null

    @Column(name = "provider_id", nullable = false)
    var providerId: String? = null

    @Column(name = "login_id", nullable = false)
    var loginId: String? = null

    @Column(name = "name", nullable = false, length = 100)
    var name: String? = null
}
