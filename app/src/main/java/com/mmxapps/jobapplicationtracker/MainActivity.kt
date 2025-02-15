package com.mmxapps.jobapplicationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.mmxapps.jobapplicationtracker.screens.HomeScreen
import com.mmxapps.jobapplicationtracker.ui.theme.JobApplicationTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobApplicationTrackerTheme {
                Navigator(HomeScreen())
            }
        }
    }
}

