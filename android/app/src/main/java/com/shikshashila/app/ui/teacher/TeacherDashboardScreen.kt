package com.shikshashila.app.ui.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.TeacherDashboardData
import com.shikshashila.app.ui.student.DashboardButton
import com.shikshashila.app.ui.theme.ManropeFontFamily

// ── Website palette ─────────────────────────────────────────────────────────
private val Purple    = Color(0xFF8B5CF6)
private val Pink      = Color(0xFFEC4899)
private val Dark      = Color(0xFF374151)
private val Muted     = Color(0xFF6B7280)
private val BgFrom    = Color(0xFFDFE9F3)
private val BgTo      = Color(0xFFFFFFFF)
private val CardWhite = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    viewModel   : TeacherViewModel = hiltViewModel(),
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
                    is TeacherDashboardState.Loading -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = Purple)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Loading...", color = Muted, fontFamily = ManropeFontFamily, fontSize = 14.sp)
                        }
                    }
                    is TeacherDashboardState.Error -> {
                        val error = (uiState as TeacherDashboardState.Error).message
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
                    is TeacherDashboardState.Success -> {
                        val data = (uiState as TeacherDashboardState.Success).data
                        TeacherDashboardContent(data, onNavigateTo)
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherDashboardContent(data: TeacherDashboardData, onNavigateTo: (String) -> Unit) {
    LazyColumn(
        modifier            = Modifier.fillMaxSize(),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Profile card ──────────────────────────────────────────────────
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
                            text       = data.teacherInfo.name,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp,
                            color      = Color(0xFF0F172A)
                        )
                        Text(
                            text       = data.teacherInfo.designation,
                            fontFamily = ManropeFontFamily,
                            fontSize   = 13.sp,
                            color      = Muted
                        )
                    }
                }
            }
        }

        // ── Stat cards ─────────────────────────────────────────────────────
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Assigned Classes",
                    value    = data.stats.assignedClasses.toString(),
                    gradient = listOf(Purple, Pink)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Assigned Subjects",
                    value    = data.stats.assignedSubjects.toString(),
                    gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
                )
            }
        }

        // ── Today's Schedule ───────────────────────────────────────────────
        item {
            Text(
                text       = "Today's Schedule",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Color(0xFF0F172A)
            )
        }

        if (data.todaySchedule.isEmpty()) {
            item {
                Card(
                    modifier  = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)),
                    shape     = RoundedCornerShape(12.dp),
                    colors    = CardDefaults.cardColors(containerColor = CardWhite),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Text(
                        text       = "No classes scheduled for today.",
                        fontFamily = ManropeFontFamily,
                        fontSize   = 14.sp,
                        color      = Muted,
                        modifier   = Modifier.padding(20.dp)
                    )
                }
            }
        } else {
            items(data.todaySchedule) { schedule ->
                Card(
                    modifier  = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(10.dp)),
                    shape     = RoundedCornerShape(10.dp),
                    colors    = CardDefaults.cardColors(containerColor = CardWhite),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier              = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Purple.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.School, null, tint = Purple, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text       = schedule.className,
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 14.sp,
                                    color      = Color(0xFF0F172A)
                                )
                                Text(
                                    text       = schedule.subject,
                                    fontFamily = ManropeFontFamily,
                                    fontSize   = 12.sp,
                                    color      = Muted
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text       = schedule.startTime,
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 13.sp,
                                color      = Purple
                            )
                            Text(
                                text       = "to ${schedule.endTime}",
                                fontFamily = ManropeFontFamily,
                                fontSize   = 12.sp,
                                color      = Muted
                            )
                        }
                    }
                }
            }
        }

        // ── Quick Actions ──────────────────────────────────────────────────
        item {
            Text(
                text       = "Quick Actions",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Color(0xFF0F172A),
                modifier   = Modifier.padding(top = 4.dp)
            )
        }
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Attendance",
                    icon     = Icons.Default.CheckCircle,
                    color    = Purple,
                    onClick  = { onNavigateTo("teacher_attendance") }
                )
                DashboardButton(
                    modifier = Modifier.weight(1f),
                    title    = "Assignments",
                    icon     = Icons.Default.Assignment,
                    color    = Color(0xFFF59E0B),
                    onClick  = { onNavigateTo("teacher_assignments") }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
fun StatCard(
    modifier : Modifier = Modifier,
    title    : String,
    value    : String,
    gradient : List<Color>
) {
    Card(
        modifier  = modifier.shadow(3.dp, RoundedCornerShape(12.dp)),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Star, null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text       = value,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize   = 26.sp,
                color      = Color(0xFF0F172A)
            )
            Text(
                text       = title,
                fontFamily = ManropeFontFamily,
                fontSize   = 12.sp,
                color      = Muted
            )
        }
    }
}
