package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.entity.Deed

interface TitleDeedRepository {
    suspend fun getAllOwnedDeeds(address: String): List<Deed>
}