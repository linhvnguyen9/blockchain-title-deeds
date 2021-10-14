package com.linh.titledeed.data.repository

import android.content.Context
import com.linh.titledeed.data.local.UserPreferencesDao
import com.linh.titledeed.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(private val context: Context): UserPreferenceRepository {
    override fun getCompletedOnboarding(): Flow<Boolean> {
        return UserPreferencesDao.getCompletedOnboarding(context)
    }

    override suspend fun setCompletedOnboarding(completed: Boolean) = withContext(Dispatchers.IO) {
        UserPreferencesDao.setCompletedOnboarding(context, completed)
    }
}