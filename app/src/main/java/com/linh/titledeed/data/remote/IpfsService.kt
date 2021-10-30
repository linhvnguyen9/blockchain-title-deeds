package com.linh.titledeed.data.remote

import com.linh.titledeed.data.entity.DeedMetadataResponse
import com.linh.titledeed.data.entity.PinFileToIpfsResponse
import com.linh.titledeed.domain.entity.Sale
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface IpfsService {
    @GET
    suspend fun getMetadataFile(@Url url: String): DeedMetadataResponse

    @POST("pinning/pinJSONToIPFS")
    suspend fun pinSaleMetadataToIpfs(@Body metadata: Sale): PinFileToIpfsResponse
}