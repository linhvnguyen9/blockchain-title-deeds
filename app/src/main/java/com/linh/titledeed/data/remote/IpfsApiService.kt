package com.linh.titledeed.data.remote

import com.linh.titledeed.data.entity.pinfileipfs.PinFileIpfsResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface IpfsApiService {
    @POST("api/v0/pin/add")
    suspend fun pinFile(@Query("arg") cid: String): PinFileIpfsResponse
}