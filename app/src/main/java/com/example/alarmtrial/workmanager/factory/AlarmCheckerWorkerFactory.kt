package com.example.alarmtrial.workmanager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmtrial.manager.WorkRequestManager
import com.example.alarmtrial.repository.AlarmRepository
import com.example.alarmtrial.workmanager.worker.AlarmCheckerWorker
import com.example.alarmtrial.util.helper.AlarmNotificationHelper
import javax.inject.Inject

class AlarmCheckerWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return AlarmCheckerWorker(alarmRepository, alarmNotificationHelper, workRequestManager, appContext, params)
    }
}