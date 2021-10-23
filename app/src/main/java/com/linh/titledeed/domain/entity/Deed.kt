package com.linh.titledeed.domain.entity

data class Deed(
    val id: String,
    val address: String,
    val imageUri: String,
    val note: String,
    val areaInSquareMeters: Double,
    val issueDate: Long,
    val isShared: Boolean,
    val purpose: LandPurpose,
    val mapNo: Int,
    val landNo: Int
)