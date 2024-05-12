package com.rktuhinbd.assessmenttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rktuhinbd.assessmenttask.home.model.VideoData

@Database(entities = [VideoData::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            // if the INSTANCE is not null, then return it, if it is then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "assessment_db"
                )
                    .allowMainThreadQueries()
                    //.addMigrations(migration)
                    .build()
                INSTANCE = instance
                // return instance
                return instance
            }
        }
    }
}