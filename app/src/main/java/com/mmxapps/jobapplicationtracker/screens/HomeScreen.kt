package com.mmxapps.jobapplicationtracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmxapps.jobapplicationtracker.MainApplication

class HomeScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = MainApplication.homeViewModel
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Application Tracker")},
                    actions = {
                        IconButton(
                            onClick = {
                                navigator.push(DetailsScreen())
                            },
                            enabled = true,
                        ) {
                            Icon(Icons.Default.Add,"add application")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(5.dp),
            ) {
                val allJobs = viewModel.getAllJobsList()
                items(allJobs.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(8.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(text = allJobs[index].companyName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = allJobs[index].jobPosition, fontSize = 12.sp)
                        }
                        Column(
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Text(text = allJobs[index].appliedDate.toString()) //don't write the year unless the date is not in the last 365 days.
                            Button(
                                onClick = {
                                    viewModel.jobDetail = allJobs[index]
                                    navigator.push(DetailsScreen())
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonColors(
                                    contentColor = Color(0x90000000),
                                    containerColor = Color(0x66FF0000),
                                    disabledContentColor = Color(0x66FF0000),
                                    disabledContainerColor = Color(0x66FF0000),
                                )
                            ) {
                                Text("More")
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
