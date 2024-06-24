package com.example.alarmtrial.di

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import com.example.alarmtrial.workmanager.factory.AlarmCheckerWorkerFactory
import com.example.alarmtrial.workmanager.factory.AlarmWorkerFactory
import com.example.alarmtrial.workmanager.factory.ChildWorkerFactory
import com.example.alarmtrial.workmanager.factory.RescheduleAlarmWorkerFactory
import com.example.alarmtrial.workmanager.factory.TimerCompletedWorkerFactory
import com.example.alarmtrial.workmanager.factory.TimerRunningWorkerFactory
import com.example.alarmtrial.workmanager.factory.WrapperWorkerFactory
import com.example.alarmtrial.workmanager.worker.AlarmCheckerWorker
import com.example.alarmtrial.workmanager.worker.AlarmWorker
import com.example.alarmtrial.workmanager.worker.RescheduleAlarmWorker
import com.example.alarmtrial.workmanager.worker.TimerCompletedWorker
import com.example.alarmtrial.workmanager.worker.TimerRunningWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactoryModule(workerFactory: WrapperWorkerFactory): WorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(AlarmWorker::class)
    abstract fun bindAlarmWorker(alarmWorkerFactory: AlarmWorkerFactory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(RescheduleAlarmWorker::class)
    abstract fun bindRescheduleAlarmWorker(rescheduleAlarmWorkerFactory: RescheduleAlarmWorkerFactory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(TimerRunningWorker::class)
    abstract fun bindTimerRunningWorker(timerRunningWorkerFactory: TimerRunningWorkerFactory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(TimerCompletedWorker::class)
    abstract fun bindTimerCompletedWorker(timerCompletedWorkerFactory: TimerCompletedWorkerFactory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(AlarmCheckerWorker::class)
    abstract fun bindAlarmCheckerWorker(alarmCheckerWorkerFactory: AlarmCheckerWorkerFactory): ChildWorkerFactory
}
