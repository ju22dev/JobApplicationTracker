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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    title = { Text("Details") },
                    actions = {
                        IconButton(onClick = {
                            //do the same thing as the add screen
                            navigator.push(EditJobScreen()) // won't be called from here
                        }) { Icon(Icons.Default.Edit,"edit details")}
                    }
                )
            }

        ){ innerPadding ->

            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                item {
                    Row() {
                        Icon(Icons.Default.DateRange,"date icon")
                        Column {
                            Text("Date Applied")
                            Text(text = jobDetail.appliedDate.toString().split(" ").let {
                                return@let it[0]+", "+it[1]+" "+it[2].trimStart('0')+" "+it[5]
                            })
                        }
                    }
                    HorizontalDivider()
                    Row {
                        Icon(Icons.Default.AccountBox, "employee icon")
                        Text(text = jobDetail.jobPosition)
                    }
                    HorizontalDivider()
                    Row {
                        Icon(Icons.Default.LocationOn, "location icon")
                        Text(text = jobDetail.companyName)
                    }
                    HorizontalDivider()
                    Row {
                        Icon(Icons.Default.DateRange,"date icon")
                        Column {
                            Text(text = "Deadline")
                            Text(jobDetail.deadline.toString().split(" ").let {
                                return@let it[0]+", "+it[1]+" "+it[2].trimStart('0')+" "+it[5]
                            })
                        }

                    }
                    HorizontalDivider()
                    Row {
                        Icon(Icons.AutoMirrored.Filled.List,"list icon")
                        Text(text = jobDetail.additionalNote)
                    }
                    HorizontalDivider()



                    Column {
                        /**
                         * For resume , cover letter and transcript:
                         *  create an intent with the address of the files in the system,
                         *  so that when these are clicked here it automatically prompts the user to open them
                         *  in another file handler app in their phone.
                         */
                    }
                }
            }
        }
    }
}

