package com.rktuhinbd.assessmenttask.home.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = DataTable.TABLE_NAME)
@TypeConverters(JsonDataTypeConverter::class)
@Parcelize
data class VideoData(

    @PrimaryKey(autoGenerate = true)
    @SerializedName(DataTable.ID)
    val id: Int = 0,

    @ColumnInfo(name = DataTable.DATE_TIME)
    @SerializedName(DataTable.DATE_TIME)
    var date: String = "",

    @ColumnInfo(name = DataTable.API_DATA)
    @SerializedName(DataTable.API_DATA)
    var apiData: MutableList<ApiResponseItem> = arrayListOf()

) : Parcelable
