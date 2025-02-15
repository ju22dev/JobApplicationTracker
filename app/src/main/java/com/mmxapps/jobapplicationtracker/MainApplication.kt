package com.mmxapps.jobapplicationtracker

import android.app.Application
import androidx.room.Room
import com.mmxapps.jobapplicationtracker.db.JobsDatabase

class MainApplication: Application() {

    companion object {
        lateinit var jobsDatabase: JobsDatabase

    }

    override fun onCreate() {
        super.onCreate()
        jobsDatabase = Room.databaseBuilder(
            applicationContext,
            JobsDatabase::class.java,
            JobsDatabase.NAME
        ).build()
    }
}