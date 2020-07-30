package com.android.feedpoc

import android.app.Application
import com.android.feedpoc.data.local.AppDataBaseBuilder

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDataBaseBuilder.getInstance(this)
    }
}