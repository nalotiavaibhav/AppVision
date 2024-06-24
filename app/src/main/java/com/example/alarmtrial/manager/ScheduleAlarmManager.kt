package com.example.alarmtrial.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.alarmtrial.data.Alarm
import com.example.alarmtrial.broadcastReceiver.AlarmBroadcastReceiver
import com.example.alarmtrial.broadcastReceiver.DAYS_SELECTED
import com.example.alarmtrial.broadcastReceiver.HOUR
import com.example.alarmtrial.broadcastReceiver.IS_RECURRING
import com.example.alarmtrial.broadcastReceiver.MINUTE
import com.example.alarmtrial.broadcastReceiver.TITLE
import com.example.alarmtrial.workmanager.worker.ALARM_CHECKER_TAG
import com.example.alarmtrial.workmanager.worker.AlarmCheckerWorker
import com.example.alarmtrial.util.GlobalProperties.pendingIntentFlags
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import java.util.Calendar
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleAlarmManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val workRequestManager: WorkRequestManager,
) {

    private val handler = Handler(Looper.getMainLooper())

    fun schedule(alarm: Alarm) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(applicationContext, AlarmBroadcastReceiver::class.java).apply {
            putExtra(IS_RECURRING, alarm.isRecurring)
            putExtra(MINUTE, alarm.minute)
            putExtra(TITLE, alarm.title)
            putExtra(HOUR, alarm.hour)
            putExtra(DAYS_SELECTED, HashMap(alarm.daysSelected))
        }
        val alarmPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarm.id,
            alarmIntent,
            pendingIntentFlags,
        )
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarm.hour.toInt())
            set(Calendar.MINUTE, alarm.minute.toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        val toastText = if (alarm.isRecurring) {
            "Recurring Alarm ${alarm.title} scheduled for ${alarm.description} at ${alarm.hour}:${alarm.minute}"
        } else {
            "One Time Alarm ${alarm.title} scheduled for ${alarm.description.substringAfter('-')} at ${alarm.hour}:${alarm.minute}"
        }
        handler.post {
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
        }

        workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)

        if (alarm.isRecurring) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmPendingIntent,
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent,
            )
        }
    }

    fun cancel(alarm: Alarm) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alamIntent = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarm.id,
            alamIntent,
            pendingIntentFlags,
        )
        val toastText = "Alarm canceled for ${alarm.hour}:${alarm.minute}"

        workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)

        handler.post {
            Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()
        }

        alarmManager.cancel(alarmPendingIntent)
    }

    fun snooze() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 10)

        val alarm = Alarm(
            id = Random().nextInt(Integer.MAX_VALUE),
            hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)),
            minute = String.format("%02d", calendar.get(Calendar.MINUTE)),
            title = "Snooze",
            isScheduled = true,
        )

        schedule(alarm)
    }

    suspend fun clearScheduledAlarms(alarmsList: List<Alarm>) {
        alarmsList.asFlow().buffer().collect {
            if (it.isScheduled) {
                cancel(it)
            }
        }
    }
}
