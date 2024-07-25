package io.say.better.domain.member.application

import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.infra.enums.AwsS3Folder
import io.say.better.core.infra.service.AwsS3Service
import io.say.better.domain.member.application.impl.ConnectService
import io.say.better.domain.member.application.impl.EducatorService
import io.say.better.domain.member.application.impl.LearnerService
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.member.exception.MemberException
import io.say.better.domain.member.ui.dto.MemberRequest
import io.say.better.domain.member.ui.dto.MemberResponse
import io.say.better.global.advice.Tx
import io.say.better.global.utils.CodeUtil
import io.say.better.storage.redis.RedisUtil
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class MemberFacade(
    private val connectService: ConnectService,
    private val memberService: MemberService,
    private val educatorService: EducatorService,
    private val learnerService: LearnerService,
    private val codeUtil: CodeUtil,
    private val redisUtil: RedisUtil,
    private val awsS3Service: AwsS3Service,
) {
    fun createConnectCode(): String {
        val member = memberService.currentMember()
        val code = codeUtil.createConnectCode()
        redisUtil.setConnectCode(code, member.email)

        return code
    }

    fun connect(code: String): Boolean {
        val email =
            redisUtil.getData(code)
                ?: throw MemberException(ErrorStatus.CONNECT_CODE_NOT_VALID)

        val educatorMember = memberService.currentMember()
        val learnerMember = memberService.getMemberByEmail(email)

        connectService.connect(
            educator = educatorService.getEducatorByMember(educatorMember),
            learner = learnerService.getLearnerByMember(learnerMember),
        )

        redisUtil.deleteData(code)

        return true
    }

    fun getEducatorInfo(): MemberResponse.EducatorDTO {
        val member = memberService.currentMember()
        val educator = educatorService.getEducatorByMember(member)

        return MemberResponse.EducatorDTO(
            educator.name,
            educator.imgUrl,
        )
    }

    fun getLearnerInfo(): MemberResponse.LearnerDTO {
        val member = memberService.currentMember()
        val learner = learnerService.getLearnerByMember(member)

        return MemberResponse.LearnerDTO(
            learner.name,
            learner.age,
            learner.gender,
            learner.imgUrl,
        )
    }

    fun postEducatorInfo(
        file: MultipartFile,
        name: String,
    ) = Tx.writeable {
        val url = awsS3Service.uploadFile(file, AwsS3Folder.MEMBER)

        val member = memberService.currentMember()
        educatorService.postEducatorInfo(member, url, name)
    }

    fun postLearnerInfo(
        file: MultipartFile,
        request: MemberRequest.LearnerInitialInfoDTO,
    ) = Tx.writeable {
        val url = awsS3Service.uploadFile(file, AwsS3Folder.MEMBER)

        val member = memberService.currentMember()
        learnerService.postLearnerInfo(member, url, request)
    }
}
