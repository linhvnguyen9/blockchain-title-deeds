package com.linh.titledeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import net.gotev.uploadservice.UploadServiceConfig
import timber.log.Timber

@HiltAndroidApp
class BlockchainTitleDeedsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initHawk()
        initAndroidUploadService()
    }

    private fun initAndroidUploadService() {
        createNotificationChannel()

        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = notificationChannelID,
            debug = BuildConfig.DEBUG
        )
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initHawk() {
        Hawk.init(this).build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                notificationChannelID,
                "TestApp Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val notificationChannelID = "TestChannel"
    }
}