package com.example.alarmtrial.workmanager.worker

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.alarmtrial.manager.WorkRequestManager
import com.example.alarmtrial.broadcastReceiver.HOUR
import com.example.alarmtrial.broadcastReceiver.MINUTE
import com.example.alarmtrial.broadcastReceiver.TITLE
import com.example.alarmtrial.repository.AlarmRepository
import com.example.alarmtrial.util.helper.ALARM_WORKER_NOTIFICATION_ID
import com.example.alarmtrial.util.helper.AlarmNotificationHelper
import com.example.alarmtrial.util.helper.MediaPlayerHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.cancellation.CancellationException

@HiltWorker
class AlarmWorker @AssistedInject constructor(
    @Assisted private val alarmRepository: AlarmRepository,
    @Assisted private val alarmNotificationHelper: AlarmNotificationHelper,
    @Assisted private val mediaPlayerHelper: MediaPlayerHelper,
    @Assisted private val workRequestManager: WorkRequestManager,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            mediaPlayerHelper.prepare()
            val title = inputData.getString(TITLE) ?: ""
            val time = "${inputData.getString(HOUR)}:${inputData.getString(MINUTE)}"

            val foregroundInfo = ForegroundInfo(
                ALARM_WORKER_NOTIFICATION_ID,
                alarmNotificationHelper.getAlarmBaseNotification(title, time).build(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK else ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST
            )
            setForeground(foregroundInfo)

            mediaPlayerHelper.start()

            alarmRepository.getAlarmByTime(
                hour = time.substringBefore(':'),
                minute = time.substringAfter(':'),
                recurring = false,
            ).collectLatest {
                it?.let {
                    it.isScheduled = false
                    alarmRepository.update(it)
                }
            }

            Result.success()
        } catch (e: CancellationException) {
            alarmNotificationHelper.removeAlarmWorkerNotification()
            mediaPlayerHelper.release()
            workRequestManager.enqueueWorker<AlarmCheckerWorker>(ALARM_CHECKER_TAG)
            Result.failure()
        }
    }
}

const val ALARM_TAG = "alarmTag"
