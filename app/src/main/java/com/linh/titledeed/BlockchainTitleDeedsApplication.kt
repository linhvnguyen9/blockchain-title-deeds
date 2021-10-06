package com.linh.titledeed

import android.app.Application
import timber.log.Timber

class BlockchainTitleDeedsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}