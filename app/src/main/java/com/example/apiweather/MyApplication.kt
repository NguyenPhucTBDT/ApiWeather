package com.example.apiweather

import android.app.Application
import okhttp3.internal.Internal.instance

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance(): MyApplication = instance
    }
}
