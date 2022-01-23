package com.example.expirydatetracker.ui.di.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.expirydatetracker.R
import com.example.expirydatetracker.domain.useCase.GetCheckItemsExpiryUseCase
import com.example.expirydatetracker.domain.useCase.UpdateStatusExpiryUseCase
import com.example.expirydatetracker.ui.expiryItems.ExpireItemsFragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class WorkerModule @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getCheckItemsExpiryUseCase: GetCheckItemsExpiryUseCase,
    private val updateStatusExpiryUseCase: UpdateStatusExpiryUseCase
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        return try {
            val notificationId = 1
            val notificationIntent = Intent(applicationContext, ExpireItemsFragment::class.java)
            val pendingIntent =
                PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            getCheckItemsExpiryUseCase.execute().forEach {
                val notification =
                    NotificationCompat.Builder(applicationContext, "com.example.expirydatetracker")
                        .setContentTitle("check expiry items")
                        .setContentText("${it.name} is expiry in ${it.date}")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .build()
                notificationManager.notify(notificationId, notification)

                updateStatusExpiryUseCase.execute(it.id)
            }
            Log.i("WorkerModule", "*********")

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}