package com.linh.titledeed.domain.usecase

import com.linh.titledeed.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SetCompletedOnboardingUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {
    suspend operator fun invoke(completed: Boolean) = userPreferenceRepository.setCompletedOnboarding(completed)
}