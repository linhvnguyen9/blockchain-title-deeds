package com.linh.titledeed.data.entity

data class UploadSaleMetadataRequest(
    val tokenId: String,
    val title: String,
    val description: String,
    val phoneNumber: String,
    val imageUrls: List<String>
)