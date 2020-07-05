package com.waqar.stickynotes

import android.app.Application
import database.AppDatabase

class MyApplication : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate()
    {
        super.onCreate()
        appDatabase = AppDatabase.getAppDatabase(this)!!
    }
}