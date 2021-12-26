package com.linh.titledeed.data.entity.deedmetadata

import com.google.gson.annotations.SerializedName

data class DeedMetadataRequest(
    val address: String,
    @SerializedName("image") val imageUri: String,
    val note: String,
    val areaInSquareMeters: Double,
    val issueDate: String,
    val isShared: Boolean,
    val purpose: String,
    val mapNo: Int,
    val landNo: Int
)