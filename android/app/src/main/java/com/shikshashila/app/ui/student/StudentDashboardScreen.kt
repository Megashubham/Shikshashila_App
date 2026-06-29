package com.shikshashila.app.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.StudentDashboardData
import com.shikshashila.app.ui.theme.ManropeFontFamily

// ── Website palette ─────────────────────────────────────────────────────────
private val Purple    = Color(0xFF8B5CF6)
private val Pink      = Color(0xFFEC4899)
private val Dark      = Color(0xFF374151)
private val Muted     = Color(0xFF6B7280)
private val BgFrom    = Color(0xFFDFE9F3)
private val BgTo      = Color(0xFFFFFFFF)
private val CardWhite = Color(0xFFFFFFFF)
private val Success   = Color(0xFF2DC58C)
private val ErrorRed  = Color(0xFFDF3C4E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    viewModel   : StudentViewModel = hiltViewModel(),
    onNavigateTo: (String) -> Unit,
    onLogout    : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(BgFrom, BgTo),
                    start  = Offset(0f, Float.POSITIVE_INFINITY),
                    end    = Offset(0f, 0f)
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎓", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text       = "Shikshashila",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize   = 18.sp,
                                color      = Color(0xFF0F172A)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor         = Color.White,
                        titleContentColor      = Color(0xFF0F172A),
                        actionIconContentColor = Dark
                    ),
                    actions = {
                        IconButton(onClick = {
                            viewModel.logout()
                            onLogout()
                        }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Dark)
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                when (uiState) {
                    is DashboardState.Loading -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = Purple)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Loading...", color = Muted, fontFamily = ManropeFontFamily, fontSize = 14.sp)
                        }
                    }
                    is DashboardState.Error -> {
                        val error = (uiState as DashboardState.Error).message
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFFEE2E2)) {
                                Text(
                                    text     = error,
                                    color    = Color(0xFFDC2626),
                                    fontSize = 14.sp,
                                    fontFamily = ManropeFontFamily,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.fetchDashboard() },
                                shape   = RoundedCornerShape(8.dp),
                                colors  = ButtonDefaults.buttonColors(containerColor = Dark)
                            ) {
                                Text("Retry", fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    is DashboardState.Success -> {
                        val data = (uiState as DashboardState.Success).data
                        DashboardContent(data, onNavigateTo)
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardContent(data: StudentDashboardData, onNavigateTo: (String) -> Unit) {
    LazyColumn(
        modifier            = Modifier.fillMaxSize(),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Profile card ─────────────────────────────────────────────────
        item {
            Card(
                modifier  = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(12.dp)),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Purple, Pink))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text       = data.studentInfo.name,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp,
                            color      = Color(0xFF0F172A)
                        )
                        Text(
                            text       = "Reg No: ${data.studentInfo.registrationNo}",
                            fontFamily = ManropeFontFamily,
                            fontSize   = 13.sp,
                            color      = Muted
                        )
                        Text(
                            text       = "Class ${data.studentInfo.className} · ${data.studentInfo.sectionName}",
                            fontFamily = ManropeFontFamily,
                            fontSize   = 13.sp,
                            color      = Purple,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // ── Attendance card ───────────────────────────────────────────────
        item {
            Card(
                modifier  = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(12.dp)),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text       = "Current Month Attendance",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                        color      = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AttendanceStat("Total",   data.attendance.currentMonth.total.toString(),   Color(0xFF0F172A))
                        AttendanceStat("Present", data.attendance.currentMonth.present.toString(), Success)
                        AttendanceStat("Absent",  data.attendance.currentMonth.absent.toString(),  ErrorRed)
                        AttendanceStat("%",       "${data.attendance.currentMonth.percentage}%",   Purple)
                    }
                }
            }
        }

        // ── Quick Actions ─────────────────────────────────────────────────
        item {
            Text(
                text       = "Quick Actions",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Color(0xFF0F172A)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Routine",
                    icon     = Icons.Default.CalendarToday,
                    color    = Purple,
                    onClick  = { onNavigateTo("student_routine") }
                )
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Results",
                    icon     = Icons.Default.Stars,
                    color    = Color(0xFF10B981),
                    onClick  = { onNavigateTo("student_results") }
                )
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Homework",
                    icon     = Icons.Default.Book,
                    color    = Color(0xFFF59E0B),
                    onClick  = { onNavigateTo("student_homework") }
                )
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Fees",
                    icon     = Icons.Default.AccountBalance,
                    color    = Color(0xFF3B82F6),
                    onClick  = { onNavigateTo("student_fees") }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardButton(
    modifier: Modifier = Modifier,
    title   : String,
    icon    : ImageVector,
    color   : Color,
    onClick : () -> Unit
) {
    Card(
        onClick   = onClick,
        modifier  = modifier.shadow(3.dp, RoundedCornerShape(12.dp)),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text       = title,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize   = 13.sp,
                color      = Color(0xFF0F172A)
            )
        }
    }
}

@Composable
fun AttendanceStat(label: String, value: String, valueColor: Color = Color(0xFF0F172A)) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text       = value,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize   = 24.sp,
            color      = valueColor
        )
        Text(
            text       = label,
            fontFamily = ManropeFontFamily,
            fontSize   = 12.sp,
            color      = Muted
        )
    }
}
