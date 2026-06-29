package com.shikshashila.app.ui.teacher.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.AttendanceSubmission
import com.shikshashila.app.data.model.SubmitAttendanceRequest
import com.shikshashila.app.data.model.TeacherClass
import com.shikshashila.app.ui.teacher.TeacherFeatureState
import com.shikshashila.app.ui.teacher.TeacherFeatureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherAttendanceScreen(
    viewModel: TeacherFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val classesState by viewModel.classesState.collectAsState()
    val attendanceState by viewModel.attendanceState.collectAsState()
    val submitState by viewModel.submitState.collectAsState()

    var selectedClass by remember { mutableStateOf<TeacherClass?>(null) }
    // We will keep a local map of studentId -> Boolean (true = present, false = absent)
    val attendanceMap = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(Unit) {
        if (classesState is TeacherFeatureState.Idle) {
            viewModel.fetchClasses()
        }
    }
    
    // When attendance is loaded, initialize the map
    LaunchedEffect(attendanceState) {
        if (attendanceState is TeacherFeatureState.Success) {
            val students = (attendanceState as TeacherFeatureState.Success).data.students
            attendanceMap.clear()
            students.forEach {
                // Default to present if not marked, else use status
                attendanceMap[it.studentId] = it.status != "absent" 
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Take Attendance") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            
            // Step 1: Select Class
            if (selectedClass == null) {
                when (classesState) {
                    is TeacherFeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp))
                    is TeacherFeatureState.Error -> Text((classesState as TeacherFeatureState.Error).message, color = MaterialTheme.colorScheme.error)
                    is TeacherFeatureState.Success -> {
                        val classes = (classesState as TeacherFeatureState.Success).data.classes
                        Text("Select Class", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        LazyColumn {
                            items(classes) { tc ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                                    onClick = { 
                                        selectedClass = tc 
                                        // Fake date for now, in real app use DatePicker
                                        viewModel.fetchAttendance(tc.classId, tc.sectionId, "2024-03-01")
                                    }
                                ) {
                                    Text(text = tc.display, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                    }
                    else -> {}
                }
            } else {
                // Step 2: Take Attendance
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Class: ${selectedClass?.display}", fontWeight = FontWeight.Bold)
                    TextButton(onClick = { 
                        selectedClass = null 
                        attendanceMap.clear()
                    }) { Text("Change") }
                }

                when (attendanceState) {
                    is TeacherFeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp))
                    is TeacherFeatureState.Error -> Text((attendanceState as TeacherFeatureState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                    is TeacherFeatureState.Success -> {
                        val students = (attendanceState as TeacherFeatureState.Success).data.students
                        
                        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                            items(students) { student ->
                                val isPresent = attendanceMap[student.studentId] ?: true
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isPresent) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = student.name, fontWeight = FontWeight.Bold)
                                            Text(text = "Reg No: ${student.registrationNo}", style = MaterialTheme.typography.bodySmall)
                                        }
                                        Switch(
                                            checked = isPresent,
                                            onCheckedChange = { attendanceMap[student.studentId] = it }
                                        )
                                    }
                                }
                            }
                        }

                        // Submit Button
                        Button(
                            onClick = {
                                val request = SubmitAttendanceRequest(
                                    classId = selectedClass!!.classId,
                                    sectionId = selectedClass!!.sectionId,
                                    date = "2024-03-01", // Fake date
                                    records = attendanceMap.map { AttendanceSubmission(it.key, it.value) }
                                )
                                viewModel.submitAttendance(request)
                            },
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            if (submitState is TeacherFeatureState.Loading) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                            } else {
                                Text("Submit Attendance")
                            }
                        }

                        if (submitState is TeacherFeatureState.Success) {
                            AlertDialog(
                                onDismissRequest = { viewModel.resetSubmitState(); onNavigateBack() },
                                confirmButton = { TextButton(onClick = { viewModel.resetSubmitState(); onNavigateBack() }) { Text("OK") } },
                                title = { Text("Success") },
                                text = { Text((submitState as TeacherFeatureState.Success).data) }
                            )
                        } else if (submitState is TeacherFeatureState.Error) {
                            AlertDialog(
                                onDismissRequest = { viewModel.resetSubmitState() },
                                confirmButton = { TextButton(onClick = { viewModel.resetSubmitState() }) { Text("OK") } },
                                title = { Text("Error") },
                                text = { Text((submitState as TeacherFeatureState.Error).message) }
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
