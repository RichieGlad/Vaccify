package com.vaccify.app.AppController

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationController: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}