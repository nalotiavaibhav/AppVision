package com.example.alarmtrial.data
/*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val enabled:Boolean,
    val operated:Boolean,
    val hour: Int,
    val minute: Int,
    var timeInMillis : Long,
    val time: String,
    val repeatDays: String?, // e.g., "Mon,Tue,Wed"
    val isEnabled: Boolean,
    val soundUri: String?,
    val vibrationEnabled: Boolean,
    val snoozeDuration: Int,
    val snoozeEnabled: Boolean,
)
*/


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.alarmtrial.util.GlobalProperties.dateTimeFormatter
import com.example.alarmtrial.util.GlobalProperties.nextDay

@Entity(
    tableName = "alarms_list_table",
    indices = [
        Index(
            value = [
                "hour", "minute", "title", "description", "daysSelectedJson",
            ],
            unique = true,
        ),
    ],
)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var hour: String = "00",
    var minute: String = "00",
    var title: String = "",
    var description: String = "Tomorrow-${nextDay.format(dateTimeFormatter)}",
    var isScheduled: Boolean = false,
    var isRecurring: Boolean = false,
    var daysSelectedJson: String = Gson().toJson(
        mapOf(
            "Sun" to false,
            "Mon" to false,
            "Tue" to false,
            "Wed" to false,
            "Thu" to false,
            "Fri" to false,
            "Sat" to false,
        ),
    ),
) {
    val daysSelected: Map<String, Boolean>
        get() = Gson().fromJson(daysSelectedJson, object : TypeToken<Map<String, Boolean>>() {}.type)

    fun setDaysSelected(daysSelected: Map<String, Boolean>) {
        this.daysSelectedJson = Gson().toJson(daysSelected)
    }
}
