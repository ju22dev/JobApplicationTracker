package com.mmxapps.jobapplicationtracker.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmxapps.jobapplicationtracker.viewmodels.HomeViewModel

class DetailsScreen : Screen {
    @SuppressLint("ContextCastToActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = viewModel(
            viewModelStoreOwner = LocalContext.current as ComponentActivity
        )
        val navigator = LocalNavigator.currentOrThrow
        val jobDetail = viewModel.jobDetail
        var detailsText by remember { mutableStateOf(jobDetail.additionalNote) }
        Scaffold (
            topBar = {
                TopAppBar (
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                //save the current state and content then go back to parent
                                navigator.popUntil { it == HomeScreen() }
                            }
                        ) { Icon(Icons.AutoMirrored.Filled.ArrowBack,"go back button")} },
                    title = { Text("Edit") },
                    actions = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Edit,"edit details")}
                        IconButton(onClick = {}) { Icon(Icons.Default.Done,"done")}
                    }
                )
            }

        ){ innerPadding ->

            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text("THIS IS DETAILS SCREEN")
                    Row {
                        Text("Date Applied: " + jobDetail.appliedDate)
                        IconButton(onClick = {}) { Icon(Icons.Default.Edit, "edit the details") }
                        // Implement the ui when the edit button for the applied date is clicked
                    }
                    Column {
                        Text(text = jobDetail.jobPosition)
                        Text(text = jobDetail.companyName)
                    }
                    Row {
                        Text("Deadline: " + jobDetail.deadline)
                        IconButton(onClick = {}) { Icon(Icons.Default.Edit, "edit the details") }
                        // Implement the ui when the edit button for the deadline date is clicked
                    }
                    Column {
                        BasicTextField(
                            value = detailsText,
                            onValueChange = {
                                detailsText = it
                            },
                            readOnly = true
                        )
                    }
                    Column {
                        /**
                         * For resume , cover letter and transcript:
                         *  create an intent with the address of the files in the system,
                         *  so that when these are clicked here it automatically prompts the user to open them
                         *  in another file handler app in their phone.
                         */
                    }

                    /**
                     * LIKE THE DETAILS OF THE JOB
                     * INFO YOU GAVE TO THE COMPANY
                     * THE RESUME AND COVE LETTER AND OTHER STUFF THAT
                     * YOU USED TO APPLY.
                     * ADDITIONAL PERSONAL NOTES AS A REMINDER
                     * AND WHEN THE DEADLINE IS(MAYBE AUTOMATE SOME OF THE
                     * THINGS WITHOUT EXPECTING THE USER TO FILL OUT IN HERE
                     * BY JUST PROVIDING THE THE JOB LINK AND SCRAPPING IT FROM THERE...HMM
                     * ...LET'S SEE
                     */
                }

            }
        }
    }
}
