package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.repository.FileRepository
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.domain.utils.map
import javax.inject.Inject

class UploadSaleMetadataUseCase @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(sale: Sale): Resource<String> {
        val dataJson = fileRepository.encodeJson(sale)
        val filePath = fileRepository.createTempFile(dataJson)

        val uploadResponse = fileRepository.uploadFileIpfs(filePath)
        if (uploadResponse.isSuccessful() && uploadResponse.data?.isBlank() == false) {
            val pinFileResponse = fileRepository.pinFileIpfs(uploadResponse.data)
            if (pinFileResponse.isSuccessful()) {
                return uploadResponse
            }
            return pinFileResponse.map { "" }
        } else {
            return uploadResponse
        }
    }
}