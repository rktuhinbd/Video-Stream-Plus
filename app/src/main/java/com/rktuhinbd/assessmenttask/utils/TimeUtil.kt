package com.rktuhinbd.assessmenttask.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeUtil {

    fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = dateFormat.format(calendar.time)
        return formattedDateTime
    }

}