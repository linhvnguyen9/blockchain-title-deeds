package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.repository.FileRepository
import com.linh.titledeed.domain.utils.Resource
import javax.inject.Inject

class UploadSaleMetadataUseCase @Inject constructor(private val fileRepository: FileRepository) {
    suspend operator fun invoke(sale: Sale): Resource<String> {
        val dataJson = fileRepository.encodeJson(sale)
        val filePath = fileRepository.createTempFile(dataJson)

        return fileRepository.uploadFileIpfs(filePath)
    }
}