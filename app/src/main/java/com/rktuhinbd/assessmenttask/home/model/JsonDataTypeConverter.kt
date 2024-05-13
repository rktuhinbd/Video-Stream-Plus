package com.rktuhinbd.assessmenttask.home.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonDataTypeConverter {

    @TypeConverter
    fun fromJson(json: String): List<ApiResponseItem> {
        // Convert the JSON string to a List<JsonData>
        val type = object : TypeToken<List<ApiResponseItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(data: List<ApiResponseItem>): String {
        // Convert the List<JsonData> to a JSON string
        return Gson().toJson(data)
    }
}