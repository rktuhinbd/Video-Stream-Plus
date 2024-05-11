package com.rktuhinbd.assessmenttask.home.repo

import com.rktuhinbd.assessmenttask.home.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // = = = > Get Videos < = = = //
    @GET("poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/14a27bd0bcd0cd323b35ad79cf3b493dddf6216b/videos.json")
    suspend fun getVideos(): Response<ApiResponse>

}