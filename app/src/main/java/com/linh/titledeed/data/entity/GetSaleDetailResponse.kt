package com.linh.titledeed.data.entity

data class GetSaleDetailResponse(
    val sellerAddress: String,
    val itemId: String,
    val price: String,
    val isForSale: Boolean,
    val metadataUri: String
)