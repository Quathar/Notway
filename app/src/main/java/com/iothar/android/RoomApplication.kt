package com.iothar.android

import android.app.Application
import com.iothar.db.AppDatabase

class RoomApplication : Application() {

    // <<-FIELD->>
    lateinit var appDatabase: AppDatabase

    // <<-METHOD->>
    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getInstance(this)
    }

}