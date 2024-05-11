package com.rktuhinbd.assessmenttask.home.model


import com.google.gson.annotations.SerializedName

data class ApiResponseItem(
    @SerializedName("author")
    val author: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("duration")
    val duration: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("isLive")
    val isLive: Boolean = false,
    @SerializedName("subscriber")
    val subscriber: String = "",
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("uploadTime")
    val uploadTime: String = "",
    @SerializedName("videoUrl")
    val videoUrl: String = "",
    @SerializedName("views")
    val views: String = ""
)