package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class GetCompletedOnboardingUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {
    operator fun invoke() = userPreferenceRepository.getCompletedOnboarding()
}