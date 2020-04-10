package com.gov.mpcb.base

import androidx.multidex.MultiDexApplication
import com.crashlytics.android.BuildConfig
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric


class MPCBApp : MultiDexApplication() {

    companion object {
        lateinit var instance: MPCBApp
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        Stetho.initializeWithDefaults(this)

        val fabric = Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(BuildConfig.DEBUG)
            .build()
        Fabric.with(fabric)

    }
}

