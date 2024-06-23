package io.say.better.core.infra.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AwsS3Service(
    private val amazonS3Client: AmazonS3Client,
) {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    /***/
    fun uploadFile(
        file: MultipartFile,
        folderName: String,
    ): String {
        val originName = file.originalFilename

        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType

        // 폴더 이름 member
        try {
            val inputStream = file.inputStream
            amazonS3Client.putObject(bucket, "$folderName/$originName", inputStream, objectMetadata)

            return amazonS3Client.getUrl(bucket, originName).toString()
        } catch (exception: Exception) {
            throw exception
        }
    }

    fun uploadFiles(
        files: List<MultipartFile>,
        folderName: String,
    ): List<String> {
        val imageUrls = ArrayList<String>()

        for (file in files) {
            imageUrls.add(uploadFile(file, folderName))
        }

        return imageUrls
    }
}
