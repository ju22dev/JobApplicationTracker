package com.mmxapps.jobapplicationtracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmxapps.jobapplicationtracker.MainApplication
import com.mmxapps.jobapplicationtracker.db.entities.Jobs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel: ViewModel() {

    private val jobsDao = MainApplication.jobsDatabase.getJobsDao()
    var jobsList : LiveData<List<Jobs>> = jobsDao.getAllJobs()
    lateinit var jobDetail: Jobs


    fun addJob(companyName: String,
               jobPosition: String,
               appliedDate: String,
               resumeGiven : String = "",
               coverLetterGiven :String = "",
               transcriptGiven:String = "",
               additionalNote:String = "",
               deadline:String,
               stages:String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            jobsDao.addJob(
                Jobs(
                    companyName = companyName,
                    jobPosition = jobPosition,
                    appliedDate = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).parse(appliedDate)!!,
                    resumeGiven = resumeGiven,
                    coverLetterGiven = coverLetterGiven,
                    transcriptGiven = transcriptGiven,
                    additionalNote = additionalNote,
                    deadline = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).parse(deadline)!!,
                    stages = stages
                )
            )
        }
    }

    fun updateJob(companyName: String,
                  jobPosition: String,
                  appliedDate: String,
                  resumeGiven : String = "",
                  coverLetterGiven :String = "",
                  transcriptGiven:String = "",
                  additionalNote:String = "",
                  deadline:String,
                  stages:String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            jobsDao.updateJob(
                Jobs(
                    companyName = companyName,
                    jobPosition = jobPosition,
                    appliedDate = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).parse(appliedDate)!!,
                    resumeGiven = resumeGiven,
                    coverLetterGiven = coverLetterGiven,
                    transcriptGiven = transcriptGiven,
                    additionalNote = additionalNote,
                    deadline = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).parse(deadline)!!,
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