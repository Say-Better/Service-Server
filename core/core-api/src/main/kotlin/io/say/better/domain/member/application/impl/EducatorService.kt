package io.say.better.domain.member.application.impl

import io.say.better.storage.mysql.dao.repository.EducatorReadRepository
import io.say.better.storage.mysql.dao.repository.EducatorWriteRepository
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.stereotype.Service

@Service
class EducatorService(
    private val educatorReadRepository: EducatorReadRepository,
    private val educatorWriteRepository: EducatorWriteRepository,
) {
    fun getEducator(educatorMember: Member): Educator =
        educatorReadRepository.findByMemberId(educatorMember).orElse(null)
            ?: Educator.createEducator(educatorMember)

}
