package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EducatorReadRepository : JpaRepository<Educator, Long> {
    fun findByEducatorId(educatorId: Long): Optional<Educator>

    fun findByMemberId(member: Member): Optional<Educator>
}
