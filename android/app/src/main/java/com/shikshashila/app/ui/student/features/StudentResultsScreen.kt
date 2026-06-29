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
fun StudentResultsScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val resultsState by viewModel.resultsState.collectAsState()

    LaunchedEffect(Unit) {
        if (resultsState is FeatureState.Idle) {
            viewModel.fetchResults()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exam Results") },
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
            when (resultsState) {
                is FeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is FeatureState.Error -> {
                    Text(
                        text = (resultsState as FeatureState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is FeatureState.Success -> {
                    val resultsList = (resultsState as FeatureState.Success).data.results
                    if (resultsList.isEmpty()) {
                        Text("No exam results available.", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(resultsList) { result ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = result.examTypeName,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        
                                        result.subjects.forEach { subject ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(text = subject.subjectName, modifier = Modifier.weight(1f))
                                                Text(text = "${subject.marksObtained} / ${subject.totalMarks}", fontWeight = FontWeight.Bold)
                                                Text(text = " (${subject.grade})", color = MaterialTheme.colorScheme.secondary)
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
