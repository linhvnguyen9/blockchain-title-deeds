package com.linh.titledeed.data.remote

import com.linh.titledeed.data.entity.DeedMetadataResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface IpfsService {
    @GET
    suspend fun getMetadataFile(@Url url: String): DeedMetadataResponse
}