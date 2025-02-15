package com.mmxapps.jobapplicationtracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mmxapps.jobapplicationtracker.db.entities.Jobs

@Dao
interface JobsDao {

    @Query("SELECT * FROM JOBS ORDER BY deadline DESC")
    fun getAllJobs() : LiveData<List<Jobs>>

    @Insert
    fun addJob(job: Jobs)

    @Query("DELETE FROM JOBS WHERE id=:id")
    fun deleteJob(id: Int)

    @Update
    fun updateJob(job: Jobs)

}