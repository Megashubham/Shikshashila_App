package com.shikshashila.app.ui.student.features

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
import com.shikshashila.app.ui.student.FeatureState
import com.shikshashila.app.ui.student.StudentFeatureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeworkScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val homeworkState by viewModel.homeworkState.collectAsState()

    LaunchedEffect(Unit) {
        if (homeworkState is FeatureState.Idle) {
            viewModel.fetchHomework()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Homework & Assignments") },
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
            when (homeworkState) {
                is FeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is FeatureState.Error -> {
                    Text(
                        text = (homeworkState as FeatureState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is FeatureState.Success -> {
                    val homeworkList = (homeworkState as FeatureState.Success).data.homework
                    if (homeworkList.isEmpty()) {
                        Text("No homework assigned.", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(homeworkList) { hw ->
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
                                                text = hw.subject,
                                                style = MaterialTheme.typography.labelLarge,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                            Text(
                                                text = "Due: ${hw.dueDate}",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = hw.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = hw.description,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        if (hw.attachment != null) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { /* Open Attachment URL */ },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text("View Attachment")
                                            }
                                        }
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
