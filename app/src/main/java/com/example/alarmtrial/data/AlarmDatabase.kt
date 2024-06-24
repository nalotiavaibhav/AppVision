package com.example.alarmtrial.data
/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object{

        @Volatile
        private var Instance : AlarmDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) : AlarmDatabase{
            return Instance ?: synchronized(LOCK){
                createDatabase(context).also {
                    Instance = it
                }
            }
        }

        private fun createDatabase(context: Context) : AlarmDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                "alarm_db"
            ).build()
        }
    }
}
*/

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmtrial.data.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao
}
