package com.shikshashila.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShikshashilaApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
