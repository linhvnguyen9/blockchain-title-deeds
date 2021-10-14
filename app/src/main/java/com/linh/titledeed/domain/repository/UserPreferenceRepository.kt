package com.linh.titledeed.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    fun getCompletedOnboarding(): Flow<Boolean>
    suspend fun setCompletedOnboarding(completed: Boolean)
}