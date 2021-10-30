package com.linh.titledeed.data.entity

import com.google.gson.annotations.SerializedName

data class PinFileToIpfsResponse(@SerializedName("IpfsHash") val cid: String)