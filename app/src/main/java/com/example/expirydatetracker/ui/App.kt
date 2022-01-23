package com.example.expirydatetracker.ui

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.expirydatetracker.ui.di.core.WorkerModule
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(AppLifecycleTracker(applicationContext))
        createNotificationChannel()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                "com.example.expirydatetracker",
                "check expiry items",
                importance
            ).apply {
                description = "this is demo to check expiry item"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

}

class AppLifecycleTracker(private val context: Context) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Log.i("onActivityCreated", "*********")
        val serviceIntent = Intent(context.applicationContext, ExampleService::class.java)
        context.stopService(serviceIntent)

        // periodicWorkRequest has minimum period length of 15 minutes
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(WorkerModule::class.java, 16, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context.applicationContext).enqueue(periodicWorkRequest)
    }

    override fun onActivityStarted(p0: Activity) {
        Log.i("onActivityStarted", "*********")
    }

    override fun onActivityResumed(p0: Activity) {
        Log.i("onActivityResumed", "*********")
    }

    override fun onActivityPaused(p0: Activity) {
        Log.i("onActivityPaused", "*********")

    }

    override fun onActivityStopped(p0: Activity) {
        Log.i("onActivityStopped", "*********")

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        Log.i("", "onActivitySaveInstanceState*********")
    }

    override fun onActivityDestroyed(p0: Activity) {
        val serviceIntent = Intent(context.applicationContext, ExampleService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
        Log.i("", "onActivityDestroyed*********")
    }

}