package com.linh.titledeed.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocalFileService @Inject constructor(@ApplicationContext private val context: Context) {
    /**
     * @returns Path to created file
     */
    fun createTempFile(content: String): String {
        val file = File.createTempFile("temp", "", context.cacheDir)
        file.writeText(content)

        return file.path
    }
}