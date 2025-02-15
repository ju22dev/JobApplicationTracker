package com.mmxapps.jobapplicationtracker

import android.app.Application
import androidx.room.Room
import com.mmxapps.jobapplicationtracker.db.JobsDatabase
import com.mmxapps.jobapplicationtracker.viewmodels.HomeViewModel

class MainApplication: Application() {

    companion object {
        lateinit var jobsDatabase: JobsDatabase
        lateinit var homeViewModel: HomeViewModel
    }

    override fun onCreate() {
        super.onCreate()
        jobsDatabase = Room.databaseBuilder(
            applicationContext,
            JobsDatabase::class.java,
            JobsDatabase.NAME
        ).build()
        homeViewModel = HomeViewModel()
    }
}