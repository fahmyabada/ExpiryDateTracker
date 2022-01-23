package com.example.expirydatetracker.ui

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.expirydatetracker.R
import com.example.expirydatetracker.domain.useCase.GetCheckItemsExpiryUseCase
import com.example.expirydatetracker.domain.useCase.UpdateStatusExpiryUseCase
import com.example.expirydatetracker.ui.expiryItems.ExpireItemsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExampleService : Service() {

    @Inject
    lateinit var getCheckItemsExpiryUseCase: GetCheckItemsExpiryUseCase

    @Inject
    lateinit var updateStatusExpiryUseCase: UpdateStatusExpiryUseCase

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("", "onCreate*********")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("", "ExampleService*********")

        // this service for make notification foreground allow to work manager to work even if app close
        val notificationIntent = Intent(this, ExpireItemsFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, "com.example.expirydatetracker")
            .setContentTitle("check expiry items")
            .setContentText("this is demo to check expiry item")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

}