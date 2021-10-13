package com.linh.titledeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BlockchainTitleDeedsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initHawk()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initHawk() {
        Hawk.init(this).build()
    }
}