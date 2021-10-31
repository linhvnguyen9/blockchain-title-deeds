package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.TitleDeedRepository
import javax.inject.Inject

class GetTokenOwnerUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(tokenId: String) = titleDeedRepository.getTokenOwner(tokenId)
}