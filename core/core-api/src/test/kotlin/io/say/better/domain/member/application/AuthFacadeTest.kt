package io.say.better.domain.member.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.say.better.client.oauth.strategy.OauthSocialLogin
import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.Provider
import io.say.better.domain.member.application.impl.EducatorService
import io.say.better.domain.member.application.impl.LearnerService
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.member.createCommonLoginRequest
import io.say.better.domain.member.createCommonMember
import io.say.better.domain.member.createJwtToken
import io.say.better.domain.member.createLearner
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.global.jwt.service.JwtService
import io.say.better.storage.mysql.domain.entity.Member
import io.say.better.support.test.DevelopTest
import io.say.better.support.test.spec.afterRootTest

@DevelopTest
class AuthFacadeTest :
    BehaviorSpec({
        val memberService = mockk<MemberService>()
        val educatorService = mockk<EducatorService>()
        val learnerService = mockk<LearnerService>()
        val jwtService = mockk<JwtService>()

        val oauthSocialLogins = emptyList<OauthSocialLogin>()

        val authFacade = AuthFacade(memberService, educatorService, learnerService, jwtService, oauthSocialLogins)

        Given("학습자 App에서 일반 로그인을 하는 경우") {
            val appType: AppType = AppType.LEARNER
            val request: AuthRequest.CommonLoginDTO = createCommonLoginRequest()
            val member: Member = createCommonMember()

            every { memberService.getMemberByEmail(appType, any<Provider>(), request) } returns member
            every { jwtService.createServiceToken(member) } returns createJwtToken()

            When("학습자로 가입한 정보가 있으면") {
                every { learnerService.getLearner(member) } returns createLearner(member)

                Then("로그인을 성공하고 학습자 정보를 입력할 필요가 없다") {
                    val result = authFacade.login(appType, request)

                    result.memberId shouldBe member.memberId
                    result.needMemberInfo shouldBe false
                }
            }

            When("학습자로 가입한 정보가 없으면") {
                every { learnerService.getLearner(member) } returns createLearner(member, name = "")

                Then("로그인을 성공하고 학습자 정보를 입력할 필요가 있다") {
                    val result = authFacade.login(appType, request)

                    result.memberId shouldBe member.memberId
                    result.needMemberInfo shouldBe true
                }
            }
        }

        afterRootTest {
            clearAllMocks()
        }
    })
