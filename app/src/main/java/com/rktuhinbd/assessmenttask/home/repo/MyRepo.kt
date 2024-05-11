package com.rktuhinbd.assessmenttask.home.repo

import android.content.Context
import android.util.Log
import com.rktuhinbd.assessmenttask.R
import com.rktuhinbd.assessmenttask.utils.ErrorHandler
import com.rktuhinbd.assessmenttask.utils.NetworkUtils
import com.rktuhinbd.assessmenttask.utils.ResponseHandler
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val context: Context,
    private val service: ApiService
) {

    private val TAG = "_repo_"

    private val jobs: ArrayList<CompletableJob> = arrayListOf()

    /** get video data **/

    fun getVideos() = flow {

        emit(ResponseHandler.Loading())

        if (!NetworkUtils.isInternetAvailable(context = context)) {
            emit(ResponseHandler.Error(ErrorHandler(localError = context.getString(R.string.no_internet_connection_msg))))
            return@flow
        }

        val response = service.getVideos()

        if (response.isSuccessful) {
            Log.d(TAG, "Success: ${response.body()}")
            emit(ResponseHandler.Success(response.body()))
        } else {
            Log.e(TAG, "API_Error: ${response.body()}")
            emit(
                ResponseHandler.Error(
                    ErrorHandler(
                        serverError = response.raw(),
                        httpError = response.errorBody()
                    )
                )
            )
        }
    }

    fun cancelJob() {
        jobs.forEach { it.cancel() }
    }
}