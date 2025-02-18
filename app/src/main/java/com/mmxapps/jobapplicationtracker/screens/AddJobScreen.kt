package com.mmxapps.jobapplicationtracker.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmxapps.jobapplicationtracker.components.DatePickerModal
import com.mmxapps.jobapplicationtracker.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class AddJobScreen: Screen {
    @SuppressLint("ContextCastToActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel : HomeViewModel = viewModel(
            viewModelStoreOwner = LocalContext.current as ComponentActivity
        )
        var companyName by remember { mutableStateOf("") }
        var jobPosition by remember { mutableStateOf("") }
        var appliedDate by remember { mutableLongStateOf(Instant.now().toEpochMilli()) }
        var deadline by remember { mutableLongStateOf(Instant.now().toEpochMilli()) }
        var additionalNote by remember { mutableStateOf("") }

        var isEditAppliedClicked by remember { mutableStateOf(false) }
        var isEditDeadlineClicked by remember { mutableStateOf(false) }

        var showCancelDialog by remember { mutableStateOf(false) }
        var showToast by remember { mutableStateOf(false) }
        var showToast2 by remember { mutableStateOf(false) }

        var jobDetail = viewModel.latestAddedJob.observeAsState()

        /**
                         * For resume , cover letter and transcript:
                         *  create an intent with the address of the files in the system,
                         *  so that when these are clicked here it automatically prompts the user to open them
                         *  in another file handler app in their phone.
                         */
        Scaffold (
            topBar = {
                TopAppBar (
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                //save the current state and content then go back to parent
                                showCancelDialog = true

                            }
                        ) { Icon(Icons.AutoMirrored.Filled.ArrowBack,"cancel button") }
                        if (showCancelDialog) {
                            BasicAlertDialog(
                                onDismissRequest = {showCancelDialog = false},
                                modifier = Modifier,
                                properties = DialogProperties()
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .wrapContentHeight(),
                                    shape = MaterialTheme.shapes.large,
                                    tonalElevation = AlertDialogDefaults.TonalElevation
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(Icons.Default.Info,"info icon",Modifier.padding(10.dp))
                                        Text(text = "Do you wanna leave this page without saving?",
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    showCancelDialog = false
                                                },
                                                modifier = Modifier.padding(end = 5.dp)
                                            ) { Text(text = "Dismiss") }
                                            TextButton(
                                                onClick = {
                                                    showCancelDialog = false
                                                    navigator.pop()
                                                },
                                                modifier = Modifier
                                            ) { Text("Confirm")   }
                                        }

                                    }
                                }
                            }
                        }
                    }
                    ,
                    title = { Text("Edit") },
                    actions = {
                        IconButton(
                            onClick = {
                                if(companyName.isEmpty() or jobPosition.isEmpty()) {
                                    showToast = true
                                } else {
                                    showToast2 = true
                                    CoroutineScope(Dispatchers.IO).launch {
                                        viewModel.addJob(
                                            companyName = companyName,
                                            jobPosition = jobPosition,
                                            appliedDate = appliedDate,
                                            resumeGiven = "",
                                            coverLetterGiven = "",
                                            transcriptGiven = "",
                                            additionalNote = additionalNote,
                                            deadline = deadline,
                                            stages = ""
                                        )
                                        delay(500)
                                        viewModel.jobDetail = jobDetail.value!!
                                        navigator.push(DetailsScreen())
                                    }

                                }
                            }
                        ) { Icon(Icons.Default.Done,"done") }
                        if (showToast) {
                            Toast.makeText(LocalContext.current,"Important fields haven't been filled!",Toast.LENGTH_SHORT).show()
                            showToast = false
                        }
                        if (showToast2) {
                            Toast.makeText(LocalContext.current,"Saving...",Toast.LENGTH_SHORT).show()
                            showToast2 = false
                        }
                    }
                )
            }

        ){ innerPadding ->

            /**
                         * For resume , cover letter and transcript:
                         *  create an intent with the address of the files in the system,
                         *  so that when these are clicked here it automatically prompts the user to open them
                         *  in another file handler app in their phone.
                         */
            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(10.dp)
                    .fillMaxSize(),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .border(width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(Icons.Default.DateRange, "date icon", Modifier
                            .padding(start = 8.dp))
                        Text(text = "Applied on:\n"+Date(appliedDate).toString().split(" ").let {
                            return@let it[0]+", "+it[1]+" "+it[2].trimStart('0')+" "+it[5]
                        },
                            modifier = Modifier.padding(5.dp))
                        IconButton(onClick = {
                            isEditAppliedClicked = true
                        }) {
                            Icon(Icons.Default.Edit,"edit the applied date")
                        }
                        if (isEditAppliedClicked) {
                            DatePickerModal(
                                onDateSelected = {
                                    if (it != null) {
                                        appliedDate = it
                                    }
                                }
                            ){
                                isEditAppliedClicked = false
                            }
                        }

                    }
                    Column {
                        TextField(
                            value = companyName,
                            onValueChange = {
                                companyName = it
                            },
                            label = { Text(text = "Company Name") },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillParentMaxWidth()
                        )
                        TextField(
                            value = jobPosition,
                            onValueChange = {
                                jobPosition = it
                            },
                            label = { Text(text = "Job Position") },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillParentMaxWidth()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .border(width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(Icons.Default.DateRange, "date icon", Modifier
                            .padding(start = 8.dp))
                        Text(text = "Deadline:\n"+Date(deadline).toString().split(" ").let {
                            return@let it[0]+", "+it[1]+" "+it[2].trimStart('0')+" "+it[5]
                        },
                            modifier = Modifier.padding(5.dp))
                        IconButton(onClick = {
                            isEditDeadlineClicked = true
                        }) {
                            Icon(Icons.Default.Edit,"edit the deadline date")
                        }
                        if (isEditDeadlineClicked) {
                            DatePickerModal(
                                onDateSelected = {
                                    if (it != null) {
                                        deadline = it
                                    }
                                }
                            ){
                                isEditDeadlineClicked = false
                            }
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .border(1.dp,MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                            .requiredHeightIn(min = 200.dp)
                    ) {
                        BasicTextField(
                            value = additionalNote,
                            onValueChange = {
                                additionalNote = it
                            },
                            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .padding(5.dp)

                                ) {

                                    if(additionalNote.isEmpty()) {
                                        Text(
                                            text = "Additional Notes",
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7F)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                    Column {
                    }

                    /**
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