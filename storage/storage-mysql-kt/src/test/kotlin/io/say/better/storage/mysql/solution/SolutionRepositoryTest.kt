package io.say.better.storage.mysql.solution

import io.say.better.core.enums.Provider
import io.say.better.core.enums.RoleType
import io.say.better.storage.mysql.MysqlContextTest
import io.say.better.storage.mysql.dao.repository.EducatorWriteRepository
import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
import io.say.better.storage.mysql.dao.repository.MemberWriteRepository
import io.say.better.storage.mysql.dao.repository.SolutionWriteRepository
import io.say.better.storage.mysql.domain.constant.ProgressState
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner
import io.say.better.storage.mysql.domain.entity.Member
import io.say.better.storage.mysql.domain.entity.Solution
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SolutionRepositoryTest(
    val solutionWriteRepository: SolutionWriteRepository,
    val memberWriteRepository: MemberWriteRepository,
    val educatorWriteRepository: EducatorWriteRepository,
    val learnerWriteRepository: LearnerWriteRepository,
) : MysqlContextTest() {

    private lateinit var member: Member
    private lateinit var educator: Educator
    private lateinit var learner: Learner
    private lateinit var solution: Solution

    @BeforeEach
    fun setUp() {
        member =
            memberWriteRepository.save(
                Member.createMember(
                    "testEmail@gmail.com",
                    "20100304",
                    RoleType.EDUCATOR_LEARNER,
                    Provider.GOOGLE,
                    "testProviderId",
                    "testLoginId",
                    "testName",
                )
            )

        educator =
            educatorWriteRepository.save(
                Educator.createEducator(
                    member,
                    "testEducatorName",
                    "20100304"
                ),
            )

        learner =
            learnerWriteRepository.save(
                Learner.createLearner(
                    member,
                    "testLearnerName",
                    "20100304"
                ),
            )

        solution =
            Solution(
                nowState = ProgressState.READY,
                educationGoal = "testEducationGoal",
                description = "testDescription",
                writer = educator,
                learner = learner,
                title = "testTitle",
                commOptTimes = 1,
                commOptCnt = 1,
            )

    }

    @Nested
    inner class SaveSolutionTest {
        @DisplayName("DB에 저장 후 반환된 Solution과 기존 Solution을 비교하면 서로 일치한다.")
        @Test
        fun compareOriginalTest() {
            //  Given
            solution.onActivated() //  null 방지를 위해 생성 단계에서 임의로 추가

            //  When
            val savedSolution = solutionWriteRepository.save(solution)

            //  Then
            Assertions.assertThat(savedSolution).isEqualTo(solution)
        }

        @DisplayName("DB에 저장 후 반환된 Solution과 다른 Solution을 비교하면 서로 일치하지 않는다.")
        @Test
        fun compareDifferentTest() {
            //  Given
            val originalSolution = solution
            originalSolution.onActivated() //  null 방지를 위해 생성 단계에서 임의로 추가

            val otherSolution = Solution(
                nowState = ProgressState.READY,
                educationGoal = "testEducationGoal",
                description = "testDescription",
                writer = educator,
                learner = learner,
                title = "testTitle",
                commOptTimes = 1,
                commOptCnt = 1,
            )

            //  When
            val savedSolution = solutionWriteRepository.save(originalSolution)

            //  Then
            Assertions.assertThat(savedSolution).isNotEqualTo(otherSolution)
        }
    }
}
