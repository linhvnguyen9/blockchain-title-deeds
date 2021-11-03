package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.entity.Sale
import com.linh.titledeed.domain.repository.TitleDeedRepository
import javax.inject.Inject

class UploadSaleMetadataUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(sale: Sale) = titleDeedRepository.uploadSaleMetadata(sale)
}