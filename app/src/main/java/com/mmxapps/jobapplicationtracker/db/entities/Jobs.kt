package com.mmxapps.jobapplicationtracker.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Jobs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val companyName: String,
    val jobPosition: String,
    val appliedDate: Date,
    val resumeGiven: String?,
    val coverLetterGiven: String?,
    val transcriptGiven: String?,
    val additionalNote: String,
    val deadline: Date,
    val stages: MutableList<String>?
    )
