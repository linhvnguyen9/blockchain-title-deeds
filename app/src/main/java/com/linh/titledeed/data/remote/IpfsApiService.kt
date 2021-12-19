package com.linh.titledeed.data.remote

import retrofit2.http.POST
import retrofit2.http.Query

interface IpfsApiService {
    @POST("api/v0/pin/add")
    fun pinFile(@Query("arg") cid: String)
}