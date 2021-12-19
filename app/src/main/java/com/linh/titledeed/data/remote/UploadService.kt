package com.linh.titledeed.data.remote

import android.content.Context
import com.linh.titledeed.BuildConfig
import com.linh.titledeed.domain.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.BaseRequestObserver
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UploadService @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun uploadFile(path: String): Resource<String> {
        val uploadId =
            MultipartUploadRequest(context, serverUrl = "${BuildConfig.IPFS_API_URL}api/v0/add")
                .setMethod("POST")
                .addFileToUpload(
                    filePath = path,
                    parameterName = "myFile"
                )
                .startUpload()

        val data = suspendCoroutine<String> { cont ->
            val requestObserverDelegate = object : RequestObserverDelegate {
                override fun onCompleted(context: Context, uploadInfo: UploadInfo) {

                }

                override fun onCompletedWhileNotObserving() {
                }

                override fun onError(
                    context: Context,
                    uploadInfo: UploadInfo,
                    exception: Throwable
                ) {
                    Timber.e("uploadInfo $uploadInfo exception $exception")
                    cont.resume("")
                }

                override fun onProgress(context: Context, uploadInfo: UploadInfo) {}

                override fun onSuccess(
                    context: Context,
                    uploadInfo: UploadInfo,
                    serverResponse: ServerResponse
                ) {
                    try {
                        cont.resume(serverResponse.bodyString)
                    } catch (e: IllegalStateException) {

                    }
                }
            }

            val requestObserver = BaseRequestObserver(context, requestObserverDelegate) {
                it.uploadId == uploadId
            }

            requestObserver.register()
        }

        return if (data.isNotEmpty()) {
            Resource.success(data)
        } else {
            Resource.error(Exception())
        }
    }
}