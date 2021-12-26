package com.linh.titledeed.domain.usecase

import com.linh.titledeed.data.entity.deedmetadata.DeedMetadataRequest
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.repository.FileRepository
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.domain.utils.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UploadDeedMetadataToIpfsUseCase @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(deed: Deed): Resource<String> {
        val uploadImageResponse = fileRepository.uploadFileIpfs(deed.imageUri)
        if (uploadImageResponse.isSuccessful() && uploadImageResponse.data?.isBlank() == false) {
            val pinFileResponse = fileRepository.pinFileIpfs(uploadImageResponse.data)
            with(deed) {
                if (pinFileResponse.isSuccessful()) {
                    val dataJson = fileRepository.encodeJson(
                        DeedMetadataRequest(
                            address,
                            "ipfs://${uploadImageResponse.data}",
                            note,
                            areaInSquareMeters,
                            issueDate.formatDate(),
                            isShared,
                            purpose.name,
                            mapNo,
                            landNo
                        )
                    )
                    val filePath = fileRepository.createTempFile(dataJson)
                    val uploadMetadataResponse = fileRepository.uploadFileIpfs(filePath)
                    if (uploadMetadataResponse.isSuccessful()) {
                        val pinMetadataResponse = fileRepository.pinFileIpfs(uploadImageResponse.data)
                        return uploadMetadataResponse
                    }
                    return uploadMetadataResponse
                }
            }
            return pinFileResponse.map { "" }
        } else {
            return uploadImageResponse
        }
    }

    fun Long.formatDate(): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = this

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(cal.time)
    }
}