package com.linh.titledeed.data.local

import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

class JsonService @Inject constructor(private val gson: Gson) {
    fun toJson(data: Any): String {
        return gson.toJson(data)
    }

    fun <T: Any> fromJson(json: String, type: Class<T>): T {
        return gson.fromJson(json, type)
    }
}