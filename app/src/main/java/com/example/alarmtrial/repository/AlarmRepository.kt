package com.example.alarmtrial.repository

import com.example.alarmtrial.data.AlarmDao
import com.example.alarmtrial.data.Alarm
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val alarmDao: AlarmDao,
) {
    val alarmsList = alarmDao.getAlarmsList().distinctUntilChanged()

    suspend fun insert(alarm: Alarm) = alarmDao.insert(alarm)

    suspend fun update(alarm: Alarm) = alarmDao.update(alarm)

    suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)

    suspend fun getLastId() = alarmDao.getLastId()

    suspend fun clear() = alarmDao.clear()

    suspend fun getAlarmById(id: Int) = alarmDao.getAlarmById(id)

    fun getAlarmByTime(hour: String, minute: String, recurring: Boolean) =
        alarmDao.getAlarmByTime(hour, minute, recurring)
}

