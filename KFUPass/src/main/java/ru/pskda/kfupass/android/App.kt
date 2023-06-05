package ru.pskda.kfupass.android

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    private var resources: Resources? = null

    override fun onCreate() {
        super.onCreate()
        resources = getResources()
    }

    fun getAppResources(): Resources? {
        return resources
    }
}