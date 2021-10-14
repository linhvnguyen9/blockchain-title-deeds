package com.linh.titledeed.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object UserPreferencesDao {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun getCompletedOnboarding(context: Context): Flow<Boolean> {
        return context.dataStore.data.map {
            it[USER_PREFERENCES_COMPLETED_ONBOARDING] ?: false
        }
    }

    suspend fun setCompletedOnboarding(context: Context, completedOnboarding: Boolean) {
        context.dataStore.edit { settings ->
            settings[USER_PREFERENCES_COMPLETED_ONBOARDING] = completedOnboarding
        }
    }

    private const val USER_PREFERENCES_COMPLETED_ONBOARDING_KEY =
        "USER_PREFERENCES_COMPLETED_ONBOARDING"
    private val USER_PREFERENCES_COMPLETED_ONBOARDING = booleanPreferencesKey(
        USER_PREFERENCES_COMPLETED_ONBOARDING_KEY
    )
}
