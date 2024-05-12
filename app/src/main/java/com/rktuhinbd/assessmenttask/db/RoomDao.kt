package com.rktuhinbd.assessmenttask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rktuhinbd.assessmenttask.home.model.ApiResponse
import com.rktuhinbd.assessmenttask.home.model.DataTable
import com.rktuhinbd.assessmenttask.home.model.VideoData

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(data: VideoData)

    @Query("SELECT * FROM ${DataTable.TABLE_NAME}")
    fun getApiData(): LiveData<VideoData>

}