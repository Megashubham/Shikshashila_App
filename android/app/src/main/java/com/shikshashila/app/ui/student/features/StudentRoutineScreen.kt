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
fun StudentRoutineScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val routineState by viewModel.routineState.collectAsState()

    LaunchedEffect(Unit) {
        if (routineState is FeatureState.Idle) {
            viewModel.fetchRoutine()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Routine") },
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
            when (routineState) {
                is FeatureState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is FeatureState.Error -> {
                    Text(
                        text = (routineState as FeatureState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is FeatureState.Success -> {
                    val routineMap = (routineState as FeatureState.Success).data.routine
                    if (routineMap.isEmpty()) {
                        Text("No routine available.", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            routineMap.forEach { (day, slots) ->
                                item {
                                    Text(
                                        text = day.uppercase(),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                items(slots) { slot ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text(text = slot.subject, fontWeight = FontWeight.Bold)
                                                Text(text = slot.teacher, style = MaterialTheme.typography.bodySmall)
                                            }
                                            Text(
                                                text = "${slot.startTime} - ${slot.endTime}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
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
