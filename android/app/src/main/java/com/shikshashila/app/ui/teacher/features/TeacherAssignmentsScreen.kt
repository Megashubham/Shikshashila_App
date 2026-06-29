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
import com.shikshashila.app.ui.teacher.TeacherFeatureState
import com.shikshashila.app.ui.teacher.TeacherFeatureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherAssignmentsScreen(
    viewModel: TeacherFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val assignmentsState by viewModel.assignmentsState.collectAsState()

    LaunchedEffect(Unit) {
        if (assignmentsState is TeacherFeatureState.Idle) {
            viewModel.fetchAssignments()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignments") },
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
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (assignmentsState) {
                is TeacherFeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is TeacherFeatureState.Error -> {
                    Text(
                        text = (assignmentsState as TeacherFeatureState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TeacherFeatureState.Success -> {
                    val assignments = (assignmentsState as TeacherFeatureState.Success).data.assignments
                    if (assignments.isEmpty()) {
                        Text("No assignments available.", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(assignments) { assign ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${assign.className} - ${assign.subject}",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                            Text(
                                                text = "Due: ${assign.dueDate}",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = assign.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = assign.description,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
