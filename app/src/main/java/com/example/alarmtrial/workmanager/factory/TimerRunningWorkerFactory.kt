package com.example.alarmtrial.workmanager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmtrial.manager.TimerManager
import com.example.alarmtrial.workmanager.worker.TimerRunningWorker
import com.example.alarmtrial.util.helper.TimerNotificationHelper
import javax.inject.Inject

class TimerRunningWorkerFactory @Inject constructor(
    private val timerManager: TimerManager,
    private val timerNotificationHelper: TimerNotificationHelper,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return TimerRunningWorker(timerManager, timerNotificationHelper, appContext, params)
    }
}
