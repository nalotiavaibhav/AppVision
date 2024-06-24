package com.example.alarmtrial.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Data
import com.example.alarmtrial.manager.ScheduleAlarmManager
import com.example.alarmtrial.manager.WorkRequestManager
import com.example.alarmtrial.workmanager.worker.ALARM_TAG
import com.example.alarmtrial.workmanager.worker.AlarmWorker
import com.example.alarmtrial.workmanager.worker.RESCHEDULE_ALARM_TAG
import com.example.alarmtrial.workmanager.worker.RescheduleAlarmWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduleAlarmManager: ScheduleAlarmManager

    @Inject
    lateinit var workRequestManager: WorkRequestManager

    private val broadcastReceiverScope = CoroutineScope(SupervisorJob())

    override fun onReceive(p0: Context?, p1: Intent?) {
        val pendingResult: PendingResult = goAsync()
        broadcastReceiverScope.launch(Dispatchers.Default) {
            try {
                p1?.let { intent ->
                    when (intent.action) {
                        "android.intent.action.BOOT_COMPLETED" -> {
                            workRequestManager.enqueueWorker<RescheduleAlarmWorker>(
                                RESCHEDULE_ALARM_TAG,
                            )
                        }
                        ACTION_DISMISS -> workRequestManager.cancelWorker(ALARM_TAG)
                        ACTION_SNOOZE -> {
                            scheduleAlarmManager.snooze()
                            workRequestManager.cancelWorker(ALARM_TAG)
                        }
                        else -> {
                            val isRecurring = intent.getBooleanExtra(IS_RECURRING, false)
                            val shouldStartWorker = !isRecurring || alarmIsToday(intent)
                            val inputData = Data.Builder()
                                .putString(TITLE, intent.getStringExtra(TITLE))
                                .putString(HOUR, intent.getStringExtra(HOUR))
                                .putString(MINUTE, intent.getStringExtra(MINUTE))
                                .build()
                            if (shouldStartWorker) {
                                workRequestManager.enqueueWorker<AlarmWorker>(
                                    ALARM_TAG,
                                    inputData,
                                )
                            }
                        }
                    }
                }
            } finally {
                pendingResult.finish()
                broadcastReceiverScope.cancel()
            }
        }
    }
}

private fun alarmIsToday(intent: Intent): Boolean {
    val daysSelected: HashMap<String, Boolean>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val serializable = intent.getSerializableExtra(DAYS_SELECTED, HashMap::class.java)
        if (serializable is HashMap<*, *>) {
            @Suppress("UNCHECKED_CAST")
            serializable as? HashMap<String, Boolean>
        } else {
            null
        }
    } else {
        @Suppress("DEPRECATION")
        val serializable = intent.getSerializableExtra(DAYS_SELECTED)
        if (serializable is HashMap<*, *>) {
            @Suppress("UNCHECKED_CAST")
            serializable as? HashMap<String, Boolean>
        } else {
            null
        }
    }
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return daysSelected?.get(getDayOfWeek(today)) ?: false
}

private fun getDayOfWeek(day: Int): String {
    return when (day) {
        Calendar.SUNDAY -> "Sunday"
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        else -> "Unknown"
    }
}


const val IS_RECURRING = "IS_RECURRING"
const val DAYS_SELECTED = "DAYS_SELECTED"
const val TITLE = "TITLE"
const val HOUR = "HOUR"
const val MINUTE = "MINUTE"
const val ACTION_DISMISS = "ACTION_DISMISS"
const val ACTION_SNOOZE = "ACTION_SNOOZE"
