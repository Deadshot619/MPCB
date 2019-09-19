package com.example.mpcb.base

import android.app.Application
import com.facebook.stetho.Stetho

class MPCBApp : Application() {

    companion object {
        lateinit var instance: MPCBApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Stetho.initializeWithDefaults(this)
    }
}

