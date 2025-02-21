package com.mmxapps.jobapplicationtracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmxapps.jobapplicationtracker.MainApplication
import com.mmxapps.jobapplicationtracker.db.entities.Jobs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel: ViewModel() {

    private val jobsDao = MainApplication.jobsDatabase.getJobsDao()
    var jobsList : LiveData<List<Jobs>> = jobsDao.getAllJobs()
    lateinit var jobDetail: Jobs
    var latestAddedJob : LiveData<Jobs> = jobsDao.getLatestAddedJob()


    fun addJob(companyName: String,
               jobPosition: String,
               appliedDate: Long,
               resumeGiven : String = "",
               coverLetterGiven :String = "",
               transcriptGiven:String = "",
               additionalNote:String = "",
               deadline:Long,
               stages:String
    ) {

        jobsDao.addJob(
            Jobs(
                companyName = companyName,
                jobPosition = jobPosition,
                appliedDate = Date(appliedDate),
                resumeGiven = resumeGiven,
                coverLetterGiven = coverLetterGiven,
                transcriptGiven = transcriptGiven,
                additionalNote = additionalNote,
                deadline = Date(deadline),
                stages = stages
            )
        )


    }

    fun updateJob(
        companyName: String,
        jobPosition: String,
        appliedDate: Long,
        resumeGiven: String = "",
        coverLetterGiven: String = "",
        transcriptGiven: String = "",
        additionalNote: String = "",
        deadline: Long,
        stages: String,
        id: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            jobsDao.updateJob(
                Jobs(
                    id = id,
                    companyName = companyName,
                    jobPosition = jobPosition,
                    appliedDate = Date(appliedDate),
                    resumeGiven = resumeGiven,
                    coverLetterGiven = coverLetterGiven,
                    transcriptGiven = transcriptGiven,
                    additionalNote = additionalNote,
                    deadline = Date(deadline),
                    stages = stages
                )
            )
        }
    }

    fun deleteJob(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            jobsDao.deleteJob(id)
        }
    }
}