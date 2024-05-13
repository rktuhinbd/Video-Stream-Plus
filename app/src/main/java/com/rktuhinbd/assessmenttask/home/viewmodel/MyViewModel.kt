package com.rktuhinbd.assessmenttask.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rktuhinbd.assessmenttask.db.RoomDao
import com.rktuhinbd.assessmenttask.home.model.ApiResponse
import com.rktuhinbd.assessmenttask.home.model.VideoData
import com.rktuhinbd.assessmenttask.home.repo.MyRepo
import com.rktuhinbd.assessmenttask.utils.ErrorHandler
import com.rktuhinbd.assessmenttask.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepo: MyRepo,
    private val roomDao: RoomDao
) : ViewModel() {

    /** Get Video Data **/
    private val _videoData =
        MutableStateFlow<ResponseHandler<ApiResponse>>(ResponseHandler.Empty())
    val videoDataObserver = _videoData.asStateFlow()

    fun getVideos() {
        viewModelScope.launch {
            myRepo.getVideos()
                .catch {
                    _videoData.emit(ResponseHandler.Error(ErrorHandler(localError = it.localizedMessage)))
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _videoData.emit(it)
                }
        }
    }

    /** Room Database **/
    val dataObserver: LiveData<VideoData> = roomDao.getApiData()

    fun insertData(data: VideoData) {
        viewModelScope.launch {
            roomDao.insertData(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        myRepo.cancelJob()
    }
}
