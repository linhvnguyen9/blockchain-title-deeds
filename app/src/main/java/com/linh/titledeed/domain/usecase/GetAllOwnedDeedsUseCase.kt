package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.TitleDeedRepository
import javax.inject.Inject

class GetAllOwnedDeedsUseCase @Inject constructor(private val titleDeedRepository: TitleDeedRepository) {
    suspend operator fun invoke(address: String) = titleDeedRepository.getAllOwnedDeeds(address)
}