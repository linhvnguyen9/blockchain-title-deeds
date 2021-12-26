package com.linh.titledeed.data.remote

import com.linh.titledeed.data.entity.*
import com.linh.titledeed.data.entity.deedmetadata.DeedMetadataResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface IpfsGatewayService {
    @GET
    suspend fun getDeedMetadata(@Url url: String): DeedMetadataResponse

    @GET
    suspend fun getSaleMetadata(@Url url: String): GetSaleMetadataResponse
}