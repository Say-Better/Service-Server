package io.say.better.storage.solution

//
// import io.say.better.core.enums.Provider
// import io.say.better.domain.solution.application.converter.SolutionConverter
// import io.say.better.domain.solution.ui.dto.SolutionRequest
// import io.say.better.storage.mysql.dao.repository.EducatorWriteRepository
// import io.say.better.storage.mysql.dao.repository.LearnerWriteRepository
// import io.say.better.storage.mysql.dao.repository.SolutionWriteRepository
// import io.say.better.storage.mysql.domain.constant.ProgressState
// import io.say.better.storage.mysql.domain.entity.Educator
// import io.say.better.storage.mysql.domain.entity.Learner
// import io.say.better.storage.mysql.domain.entity.Solution
// import org.assertj.core.api.Assertions
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.DisplayName
// import org.junit.jupiter.api.Nested
// import org.junit.jupiter.api.Test
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//
// @DataJpaTest
open class SolutionRepositoryTest
//     @Autowired
//     constructor(
//         private val solutionWriteRepository: SolutionWriteRepository,
//         private val educatorWriteRepository: EducatorWriteRepository,
//         private val learnerWriteRepository: LearnerWriteRepository,
//     ) {
//         private lateinit var educator: Educator
//         private lateinit var learner: Learner
//         private lateinit var request: SolutionRequest.CreateSolution
//         private lateinit var solution: Solution
//
//         @BeforeEach
//         fun setUp() {
//             educator =
//                 educatorWriteRepository.save(
//                     Educator.createEducator(
//                         "testEducatorEmail@gmail.com",
//                         "20000104",
//                         Provider.GOOGLE,
//                         "testEducatorProviderId",
//                         "testEducatorLoginId",
//                         "testEducatorName",
//                     ),
//                 )
//
//             learner =
//                 learnerWriteRepository.save(
//                     Learner.createLearner(
//                         "testLearnerEmail@gmail.com",
//                         "20100304",
//                         Provider.GOOGLE,
//                         "testLearnerProviderId",
//                         "testLearnerLoginId",
//                         "testLearnerName",
//                     ),
//                 )
//
//             request =
//                 SolutionRequest.CreateSolution(
//                     learnerEmail = learner.email,
//                     nowState = ProgressState.READY,
//                     educationGoal = "testEducationGoal",
//                     description = "testDescription",
//                     title = "testTitle",
//                     commOptTimes = 1,
//                     commOptCnt = 1,
//                 )
//             solution = SolutionConverter.toSolution(request, educator, learner)
//         }
//
//         @Nested
//         inner class SaveSolutionTest {
//             @DisplayName("DB에 저장 후 반환된 Solution과 기존 Solution을 비교하면 서로 일치한다.")
//             @Test
//             fun compareOriginalTest() {
//                 //  Given
//                 solution.onActivated() //  null 방지를 위해 생성 단계에서 임의로 추가
//
//                 //  When
//                 val savedSolution = solutionWriteRepository.save(solution)
//
//                 //  Then
//                 Assertions.assertThat(savedSolution).isEqualTo(solution)
//             }
//
//             @DisplayName("DB에 저장 후 반환된 Solution과 다른 Solution을 비교하면 서로 일치하지 않는다.")
//             @Test
//             fun compareDifferentTest() {
//                 //  Given
//                 val originalSolution = solution
//                 originalSolution.onActivated() //  null 방지를 위해 생성 단계에서 임의로 추가
//
//                 val otherRequest =
//                     SolutionRequest.CreateSolution(
//                         learnerEmail = learner.email,
//                         nowState = ProgressState.READY,
//                         educationGoal = "testEducationGoal",
//                         description = "testDescription",
//                         title = "otherTitle",
//                         commOptTimes = 1,
//                         commOptCnt = 1,
//                     )
//                 val otherSolution = SolutionConverter.toSolution(otherRequest, educator, learner)
//
//                 //  When
//                 val savedSolution = solutionWriteRepository.save(originalSolution)
//
//                 //  Then
//                 Assertions.assertThat(savedSolution).isNotEqualTo(otherSolution)
//             }
//         }
//     }
