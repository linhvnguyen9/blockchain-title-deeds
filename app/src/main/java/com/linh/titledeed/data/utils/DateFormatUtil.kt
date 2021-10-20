package com.linh.titledeed.data.utils

import java.text.SimpleDateFormat
import java.util.*


object DateFormatUtil {
    @JvmStatic
    fun formatTime(time: Calendar?): String {
        if (time == null)
            return ""

        val myFormat = "H:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        return sdf.format(time.time)
    }

    @JvmStatic
    fun formatDate(date: Calendar?): String {
        if (date == null) {
            return ""
        }

        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        return sdf.format(date.time)
    }

    @JvmStatic
    fun formatDateTime(date: Calendar?): String {
        if(date == null)
            return "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        val myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.format(date.time)
    }

    @JvmStatic
    fun formatDateTime(date: Date?): String {
        if (date == null)
            return "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        val myFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.format(date.time)
    }

    @JvmStatic
    fun formatNullableDate(date: Calendar?): String? {
        if (date == null)
            return null

        val format = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(format)

        return sdf.format(date.time)
    }

    @JvmStatic
    fun timeStringToCalendar(string: String?): Calendar? {
        if (string == null)
            return null

        val calendar = Calendar.getInstance()
        calendar.time = timeStringToDate(string)
        return calendar
    }

    @JvmStatic
    fun timeStringToDate(string: String?): Date? {
        if (string == null)
            return null

        val formattedString = string.replace(Regex("[TZ]"), "")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        return simpleDateFormat.parse(formattedString)
    }

    @JvmStatic
    fun timeStringToDate(string: String?, format: String): Date? {
        if (string.isNullOrBlank())
            return null

        val formattedString = string.replace(Regex("[TZ]"), "")
        val simpleDateFormat = SimpleDateFormat(format)

        return simpleDateFormat.parse(formattedString)
    }

    @JvmStatic
    fun timeStringToCalendar(string: String?, format: String): Calendar? {
        if (string.isNullOrBlank()) return null

        return Calendar.getInstance().apply {
            time = timeStringToDate(string, format)
        }
    }
}
