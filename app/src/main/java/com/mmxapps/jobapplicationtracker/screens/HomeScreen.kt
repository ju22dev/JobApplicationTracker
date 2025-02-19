package com.mmxapps.jobapplicationtracker.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmxapps.jobapplicationtracker.viewmodels.HomeViewModel

class HomeScreen: Screen {
    @SuppressLint("ContextCastToActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = viewModel(
            viewModelStoreOwner = LocalContext.current as ComponentActivity
        )
        val navigator = LocalNavigator.currentOrThrow
        val allJobs by viewModel.jobsList.observeAsState(initial = listOf())
        Log.i("ubo",allJobs.toString())
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Job Application Tracker")},
                    actions = {
                        IconButton(
                            onClick = {

                                navigator.push(AddJobScreen())
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

                items(allJobs.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.AssignmentTurnedIn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(8.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp).weight(1f)
                        ) {
                            Text(text = allJobs[index].companyName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = allJobs[index].jobPosition, fontSize = 12.sp)
                        }
                        Column(
                            modifier = Modifier.padding(start = 10.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Row {
                                Icon(Icons.Default.DateRange, "date icon",Modifier.padding(end = 4.dp))
                                Text(text = allJobs[index].appliedDate.toString().split(" ").let {
                                    return@let it[0]+", "+it[1]+" "+it[2].trimStart('0')+" "+it[5]
                                })
                            }

                            Row {
                                Button(
                                    onClick = {
                                        viewModel.jobDetail = allJobs[index]
                                        navigator.push(DetailsScreen())
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.padding(end = 2.dp).size(width = 80.dp, height = 35.dp),
                                    colors = ButtonColors(
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                        containerColor = MaterialTheme.colorScheme.outline,
                                        disabledContentColor = Color(0x66FF0000),
                                        disabledContainerColor = Color(0x66FF0000),
                                    )
                                ) {
                                    Text("More", color = MaterialTheme.colorScheme.onPrimary)
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.deleteJob(id = allJobs[index].id)
                                    }
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete icon",tint = Color(0xFFFF0000))
                                }
                            }

                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
