package com.linh.titledeed.data.entity.uploadfileipfs

import com.google.gson.annotations.SerializedName

data class UploadFileIpfsResponse(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Hash")
    val hash: String,
    @SerializedName("Size")
    val size: String
)