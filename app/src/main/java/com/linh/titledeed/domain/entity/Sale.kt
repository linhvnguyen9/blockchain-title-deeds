package com.linh.titledeed.domain.entity

data class Sale(
    val tokenId: String,
    val title: String,
    val description: String,
    val phoneNumber: String,
    val imageUrls: List<String>,
    val sellerAddress: String = "",
    val price: String = "",
    val isForSale: Boolean = true,
    val deed: Deed? = null
)
