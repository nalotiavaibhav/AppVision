package com.example.alarmtrial.workmanager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmtrial.manager.ScheduleAlarmManager
import com.example.alarmtrial.manager.WorkRequestManager
import com.example.alarmtrial.repository.AlarmRepository
import com.example.alarmtrial.workmanager.worker.RescheduleAlarmWorker
import javax.inject.Inject

class RescheduleAlarmWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return RescheduleAlarmWorker(alarmRepository, scheduleAlarmManager, workRequestManager, appContext, params)
    }
}
