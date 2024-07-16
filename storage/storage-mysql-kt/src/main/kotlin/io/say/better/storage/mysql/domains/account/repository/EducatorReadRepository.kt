package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EducatorReadRepository : JpaRepository<Educator, Long> {
    fun findByEducatorId(educatorId: Long): Optional<Educator>

    fun findByMemberId(member: Member): Optional<Educator>
}
