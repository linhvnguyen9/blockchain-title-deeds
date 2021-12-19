package com.linh.titledeed.data.repository

import com.linh.titledeed.data.entity.uploadfileipfs.UploadFileIpfsResponse
import com.linh.titledeed.data.local.JsonService
import com.linh.titledeed.data.local.LocalFileService
import com.linh.titledeed.data.remote.IpfsApiService
import com.linh.titledeed.data.remote.UploadService
import com.linh.titledeed.domain.repository.FileRepository
import com.linh.titledeed.domain.utils.Resource
import com.linh.titledeed.domain.utils.map
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileService: LocalFileService,
    private val jsonService: JsonService,
    private val uploadService: UploadService,
    private val ipfsApiService: IpfsApiService,
) : FileRepository {
    override fun createTempFile(content: String): String {
        return fileService.createTempFile(content)
    }

    override fun encodeJson(data: Any): String {
        return jsonService.toJson(data)
    }

    override suspend fun uploadFileIpfs(path: String): Resource<String> {
        return uploadService.uploadFile(path).map {
            if (!it.isNullOrBlank()) {
                val response = jsonService.fromJson(it ?: "", UploadFileIpfsResponse::class.java)
                response.hash
            } else {
                ""
            }
        }
    }

    override suspend fun pinFileIpfs(cid: String): Resource<Any> {
        return Resource.success(ipfsApiService.pinFile(cid))
    }
}