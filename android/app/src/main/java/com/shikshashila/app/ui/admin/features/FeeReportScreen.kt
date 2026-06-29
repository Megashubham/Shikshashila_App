package com.shikshashila.app.ui.admin.features

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
import com.shikshashila.app.ui.admin.AdminState
import com.shikshashila.app.ui.admin.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeeReportScreen(
    viewModel: AdminViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val feesState by viewModel.feesReportState.collectAsState()

    LaunchedEffect(Unit) {
        if (feesState is AdminState.Idle) {
            viewModel.fetchFeesReport()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fee Collection Report") },
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
            when (feesState) {
                is AdminState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is AdminState.Error -> {
                    Text(
                        text = (feesState as AdminState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is AdminState.Success -> {
                    val data = (feesState as AdminState.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text("Session: ${data.session}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }

                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Financial Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Total Expected:")
                                        Text("₹${data.summary.totalExpected}", fontWeight = FontWeight.Bold)
                                    }
                                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Total Collected:")
                                        Text("₹${data.summary.totalCollected}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Total Due:")
                                        Text("₹${data.summary.totalDue}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Recent Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }

                        if (data.recentTransactions.isEmpty()) {
                            item { Text("No recent transactions.") }
                        } else {
                            items(data.recentTransactions) { txn ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = txn.studentName, fontWeight = FontWeight.Bold)
                                            Text(text = txn.receiptNo, style = MaterialTheme.typography.bodySmall)
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(text = "₹${txn.amount}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            Text(text = txn.date, style = MaterialTheme.typography.bodySmall)
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
