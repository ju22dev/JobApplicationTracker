package com.mmxapps.jobapplicationtracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmxapps.jobapplicationtracker.MainApplication
import com.mmxapps.jobapplicationtracker.db.entities.Jobs
import java.time.Instant
import java.util.Date

class HomeViewModel: ViewModel() {

    private val jobsDao = MainApplication.jobsDatabase.getJobsDao()
    var jobsList : LiveData<List<Jobs>> = jobsDao.getAllJobs()
    lateinit var jobDetail: Jobs



    fun getAllJobsList() : List<Jobs>{
        return if (!jobsList.value.isNullOrEmpty()) {
            jobsList.value!!
        } else {
            listOf()
        }

    }


    fun addJob(companyName: String,
               jobPosition: String,
               appliedDate: String,
               resumeGiven : String = "",
               coverLetterGiven :String = "",
               transcriptGiven:String = "",
               additionalNote:String = "",
               deadline:String,
               stages:MutableList<String>) {
        jobsDao.addJob(
            Jobs(
                companyName = companyName,
                jobPosition = jobPosition,
                appliedDate = Date.from(Instant.parse(appliedDate)),
                resumeGiven = resumeGiven,
                coverLetterGiven = coverLetterGiven,
                transcriptGiven = transcriptGiven,
                additionalNote = additionalNote,
                deadline = Date.from(Instant.parse(deadline)),
                stages = stages
            )
        )
    }

    fun updateJob(companyName: String,
                  jobPosition: String,
                  appliedDate: String,
                  resumeGiven : String = "",
                  coverLetterGiven :String = "",
                  transcriptGiven:String = "",
                  additionalNote:String = "",
                  deadline:String,
                  stages:MutableList<String>) {
        jobsDao.updateJob(
            Jobs(
                companyName = companyName,
                jobPosition = jobPosition,
                appliedDate = Date.from(Instant.parse(appliedDate)),
                resumeGiven = resumeGiven,
                coverLetterGiven = coverLetterGiven,
                transcriptGiven = transcriptGiven,
                additionalNote = additionalNote,
                deadline = Date.from(Instant.parse(deadline)),
                stages = stages
            )
        )
    }

    fun deleteJob(id: Int) {
        jobsDao.deleteJob(id)
    }
}