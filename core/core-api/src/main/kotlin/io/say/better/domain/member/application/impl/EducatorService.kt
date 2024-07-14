package io.say.better.domain.member.application.impl

import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.common.utils.logger
import io.say.better.domain.member.exception.MemberException
import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Member
import io.say.better.storage.mysql.domains.account.repository.EducatorReadRepository
import io.say.better.storage.mysql.domains.account.repository.EducatorWriteRepository
import org.springframework.stereotype.Service

@Service
class EducatorService(
    private val educatorReadRepository: EducatorReadRepository,
    private val educatorWriteRepository: EducatorWriteRepository,
) {
    private val log = logger()

    fun getEducator(educatorMember: Member): Educator {
        val educator = (
            educatorReadRepository.findByMemberId(educatorMember).orElse(null)
                ?: Educator.createEducator(educatorMember)
        )

        return educatorWriteRepository.save(educator)
    }

    fun getEducatorByMember(educatorMember: Member): Educator =
        educatorReadRepository
            .findByMemberId(educatorMember)
            .orElseThrow { MemberException(ErrorStatus.EDUCATOR_NOT_FOUND) }

    fun postEducatorInfo(
        member: Member,
        url: String,
        name: String,
    ) {
        val educator = getEducator(member)

        educator.initializeEducatorInfo(url, name)
    }
}
