package com.linh.titledeed.domain.repository

import com.linh.titledeed.domain.utils.Resource

interface FileRepository {
    fun createTempFile(content: String): String
    fun encodeJson(data: Any): String

    /**
     * @returns CID of uploaded file
     */
    suspend fun uploadFileIpfs(path: String): Resource<String>
    suspend fun pinFileIpfs(cid: String): Resource<Any>
}