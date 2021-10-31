package com.linh.titledeed.data.remote

import com.linh.titledeed.data.entity.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface IpfsService {
    @GET
    suspend fun getDeedMetadata(@Url url: String): DeedMetadataResponse

    @GET
    suspend fun getSaleMetadata(@Url url: String): GetSaleMetadataResponse

    @POST("pinning/pinJSONToIPFS")
    suspend fun pinSaleMetadataToIpfs(@Body metadata: UploadSaleMetadataRequest): PinFileToIpfsResponse
}