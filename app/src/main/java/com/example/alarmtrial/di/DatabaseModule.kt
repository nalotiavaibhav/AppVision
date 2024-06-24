package com.example.alarmtrial.di

import android.content.Context
import androidx.room.Room
import com.example.alarmtrial.data.AlarmDao
import com.example.alarmtrial.data.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAlarmDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        AlarmDatabase::class.java,
        "alarms_list_database",
    ).build()

    @Singleton
    @Provides
    fun provideAlarmDao(alarmDatabase: AlarmDatabase):
            AlarmDao {
        return alarmDatabase.getAlarmDao()
    }
}
