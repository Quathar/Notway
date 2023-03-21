package com.iothar.android

import android.app.Application
import com.iothar.db.AppDatabase

class RoomApplication : Application() {

    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getInstance(this)
    }

}