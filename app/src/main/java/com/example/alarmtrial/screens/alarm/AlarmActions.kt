package com.example.alarmtrial.screens.alarm

import com.example.alarmtrial.data.Alarm

interface AlarmActions {
    fun updateAlarmCreationState(alarm: Alarm) {}
    fun update(alarm: Alarm) {}
    fun remove(alarm: Alarm) {}
    fun save() {}
    fun clear() {}
}
