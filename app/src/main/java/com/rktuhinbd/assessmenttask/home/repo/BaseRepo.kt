package com.rktuhinbd.assessmenttask.home.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rktuhinbd.assessmenttask.utils.ErrorHandler
import com.rktuhinbd.assessmenttask.utils.ResponseHandler
import kotlinx.coroutines.CoroutineExceptionHandler

const val TAG = "BASE_REPO"

open class BaseRepo() {

    fun <T> getExceptionHandler(observer: MutableLiveData<ResponseHandler<T>>?): CoroutineExceptionHandler {

        return CoroutineExceptionHandler { _, exception ->
            observer?.postValue(
                ResponseHandler.Error(
                    ErrorHandler(localError = exception.message)
                )
            )
        }
    }

//    fun getHeaderMap(): Map<String, String> {
//        return if (!TextUtils.isEmpty(userId)) {
//            mapOf(
//                "Authorization" to "Bearer ${currentUser.accessToken}",
//            ).toMutableMap()
//        }
//    }

    // ==========> Post video session <========== //

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception: ${exception.localizedMessage}")
    }

}