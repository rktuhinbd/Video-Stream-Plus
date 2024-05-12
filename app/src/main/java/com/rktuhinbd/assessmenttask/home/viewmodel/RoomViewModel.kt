package com.rktuhinbd.assessmenttask.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rktuhinbd.assessmenttask.db.RoomDao
import com.rktuhinbd.assessmenttask.home.model.VideoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomDao: RoomDao
) : ViewModel() {

    val dataObserver: LiveData<VideoData> = roomDao.getApiData()

    fun insertData(data: VideoData) {
        viewModelScope.launch {
            roomDao.insertQuiz(data)
        }
    }
}