package io.say.better.support.util

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

object RestDocsUtils {
    fun requestPreprocessor(): OperationRequestPreprocessor =
        Preprocessors.preprocessRequest(
            Preprocessors
                .modifyUris()
                .scheme("https")
                .host("api.say.better.io")
                .removePort(),
            Preprocessors.prettyPrint(),
        )

    fun responsePreprocessor(): OperationResponsePreprocessor =
        Preprocessors.preprocessResponse(
            Preprocessors.prettyPrint(),
        )
}
