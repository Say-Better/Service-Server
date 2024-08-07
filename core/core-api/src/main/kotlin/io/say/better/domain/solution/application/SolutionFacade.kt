package io.say.better.domain.solution.application

import io.say.better.client.symbol.client.RecommendClient
import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.common.exception.GeneralException
import io.say.better.core.common.utils.logger
import io.say.better.core.infra.enums.AwsS3Folder
import io.say.better.core.infra.service.AwsS3Service
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.domain.review.application.impl.RecordService
import io.say.better.domain.review.application.impl.ReviewService
import io.say.better.domain.solution.application.converter.ProgressConverter
import io.say.better.domain.solution.application.converter.ReviewConverter
import io.say.better.domain.solution.application.converter.SolutionConverter
import io.say.better.domain.solution.application.converter.SolutionResponseConverter
import io.say.better.domain.solution.application.impl.ProgressService
import io.say.better.domain.solution.application.impl.SolutionProgressPublisher
import io.say.better.domain.solution.application.impl.SolutionService
import io.say.better.domain.solution.application.impl.SolutionSymbolService
import io.say.better.domain.solution.ui.dto.SolutionRequest
import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.domain.solution.ui.dto.SolutionRequest.StartSolution
import io.say.better.domain.symbol.application.impl.SymbolService
import io.say.better.global.advice.Tx
import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Learner
import io.say.better.storage.mysql.domains.progress.entity.Progress
import io.say.better.storage.mysql.domains.review.entity.Review
import io.say.better.storage.mysql.domains.solution.entity.Solution
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class SolutionFacade(
    private val solutionService: SolutionService,
    private val solutionSymbolService: SolutionSymbolService,
    private val memberService: MemberService,
    private val symbolService: SymbolService,
    private val recommendClient: RecommendClient,
    private val progressService: ProgressService,
    private val solutionProgressPublisher: SolutionProgressPublisher,
    private val reviewService: ReviewService,
    private val recordService: RecordService,
    private val awsS3Service: AwsS3Service,
) {
    private val log = logger()

    fun recommendSymbol(name: String) =
        Tx.readable {
            val recommend = recommendClient.recommend(name)
            val symbols = symbolService.getSymbols(recommend.symbols)
            return@readable SolutionResponseConverter.toSymbolRecommend(name, symbols)
        }

    fun searchSymbol(name: String) =
        Tx.readable {
            val symbols = symbolService.getSymbols(name)
            return@readable SolutionResponseConverter.toSymbolRecommend(name, symbols)
        }

    fun createSolution(request: SolutionRequest.CreateSolution) =
        Tx.writeable {
            val member = memberService.currentMember() as Educator
            val learner = memberService.getMemberByEmail(request.learnerEmail) as Learner
            val newSolution = SolutionConverter.toSolution(request, member, learner)
            val savedSolution = solutionService.createSolution(newSolution)

            savedSolution.onActivated()
        }

    fun startSolution(request: StartSolution) =
        Tx.writeable {
            val solution: Solution = solutionService.getSolution(request.solutionId)
            solution.onStart()
            val progress: Progress = ProgressConverter.toProgress(request, solution)
            progress.onActivated()
            val savedProgress = progressService.createProgress(progress)

            return@writeable SolutionResponseConverter.toProgressInfo(savedProgress)
        }

    fun endSolution(request: List<EndSolution>) =
        Tx.writeable {
            val progress: Progress = progressService.getProgress(request[0].progressId)
            val review: Review = ReviewConverter.toReview(progress)
            val savedReview: Review = reviewService.createReview(review)
            progress.solution.onEnd()

            for (endSolution in request) {
                endSolution.reviewId = savedReview.reviewId
                solutionProgressPublisher.publishRecord(endSolution)
            }
        }

    fun uploadVoiceFileOnRecord(
        voiceFile: MultipartFile,
        progressId: Long,
    ): String =
        Tx.writeable {
            val progress = progressService.getProgress(progressId)
            // Rabbit MQ에서 아직 처리중일 수 있음
            val review =
                reviewService.getReviewByProgress(progress)
                    ?: throw GeneralException(ErrorStatus.VOICE_SAVE_TARGET_RECORD_NOT_FOUND)
            // Rabbit MQ에서 아직 처리중일 수 있음
            val record =
                recordService.getRecordByReview(review)
                    ?: throw GeneralException(ErrorStatus.VOICE_SAVE_TARGET_RECORD_NOT_FOUND)

            val voiceUrl = awsS3Service.uploadFile(voiceFile, AwsS3Folder.VOICE)

            recordService.updateVoice(voiceUrl, record)

            return@writeable voiceUrl
        }
}
