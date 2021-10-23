package com.linh.titledeed.data.entity

import androidx.compose.ui.text.toUpperCase
import com.google.gson.annotations.SerializedName
import com.linh.titledeed.data.utils.DateFormatUtil.timeStringToCalendar
import com.linh.titledeed.data.utils.getHttpLinkFromIpfsUri
import com.linh.titledeed.domain.entity.Deed
import com.linh.titledeed.domain.entity.LandPurpose
import java.time.LocalDate
import java.util.*

data class DeedMetadataResponse(
    val address: String?,
    @SerializedName("image") val imageUri: String?,
    val note: String?,
    val areaInSquareMeters: Double?,
    val issueDate: String?,
    val isShared: Boolean?,
    val purpose: String?,
    val mapNo: Int?,
    val landNo: Int?
) {
    fun toDomainModel(id: String) = Deed(
        id,
        address ?: "",
        getHttpLinkFromIpfsUri(imageUri ?: ""),
        note ?: "",
        areaInSquareMeters ?: 0.0,
        timeStringToCalendar(issueDate)?.timeInMillis ?: 0L,
        isShared ?: false,
        LandPurpose.valueOf(
            purpose?.toUpperCase(
                Locale.ENGLISH
            ) ?: ""
        ),
        mapNo ?: 0,
        landNo ?: 0
    )
}