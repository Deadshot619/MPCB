package com.example.mpcb.base

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class MPCBApp : MultiDexApplication() {

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

