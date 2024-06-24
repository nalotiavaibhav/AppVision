package com.example.alarmtrial.workmanager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmtrial.manager.TimerManager
import com.example.alarmtrial.workmanager.worker.TimerCompletedWorker
import com.example.alarmtrial.util.helper.MediaPlayerHelper
import com.example.alarmtrial.util.helper.TimerNotificationHelper
import javax.inject.Inject

class TimerCompletedWorkerFactory @Inject constructor(
    private val mediaPlayerHelper: MediaPlayerHelper,
    private val timerNotificationHelper: TimerNotificationHelper,
    private val timerManager: TimerManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return TimerCompletedWorker(mediaPlayerHelper, timerNotificationHelper, timerManager, appContext, params)
    }
}
