package com.mmxapps.jobapplicationtracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmxapps.jobapplicationtracker.db.entities.Jobs

@Database(entities = [Jobs::class], version = 1)
@TypeConverters(Converters::class)
abstract class JobsDatabase : RoomDatabase() {

    companion object {
        const val NAME = "Jobs_DB"
    }

    abstract fun getJobsDao() : JobsDao

}