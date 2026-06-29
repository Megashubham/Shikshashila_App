package com.shikshashila.app.ui.teacher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.TeacherDashboardData
import com.shikshashila.app.ui.student.DashboardButton
import com.shikshashila.app.ui.theme.ManropeFontFamily

private val BlueStart  = Color(0xFF2563EB)
private val BlueEnd    = Color(0xFF3B82F6)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)

private val SoftPurple = Color(0xFFEDE9FE); private val TintPurple = Color(0xFF7C3AED)
private val SoftPink   = Color(0xFFFCE7F3); private val TintPink   = Color(0xFFDB2777)
private val SoftGreen  = Color(0xFFD1FAE5); private val TintGreen  = Color(0xFF059669)
private val SoftYellow = Color(0xFFFEF3C7); private val TintYellow = Color(0xFFD97706)
private val SoftBlue   = Color(0xFFDBEAFE); private val TintBlue   = Color(0xFF2563EB)
private val SoftRed    = Color(0xFFFEE2E2); private val TintRed    = Color(0xFFDC2626)
private val SoftTeal   = Color(0xFFCCFBF1); private val TintTeal   = Color(0xFF0D9488)
private val SoftOrange = Color(0xFFFFEDD5); private val TintOrange = Color(0xFFEA580C)

private data class TModuleEntry(val title: String, val icon: ImageVector, val bg: Color, val tint: Color, val route: String = "")

@Composable
fun TeacherDashboardScreen(
    viewModel: TeacherViewModel = hiltViewModel(),
    onNavigateTo: (String) -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(BgColor)) {
        when (uiState) {
            is TeacherDashboardState.Loading -> {
                Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = BlueStart)
                    Spacer(Modifier.height(12.dp))
                    Text("Loading...", color = TextMuted, fontFamily = ManropeFontFamily, fontSize = 14.sp)
                }
            }
            is TeacherDashboardState.Error -> {
                val msg = (uiState as TeacherDashboardState.Error).message
                Column(modifier = Modifier.align(Alignment.Center).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(shape = RoundedCornerShape(12.dp), color = SoftRed) {
                        Text(msg, color = TintRed, fontSize = 14.sp, fontFamily = ManropeFontFamily, modifier = Modifier.padding(16.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { viewModel.fetchDashboard() }, shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BlueStart)) {
                        Text("Retry", fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            is TeacherDashboardState.Success -> {
                val data = (uiState as TeacherDashboardState.Success).data
                TeacherDashboardContent(data, onNavigateTo, onLogout)
            }
        }
    }
}

@Composable
fun TeacherDashboardContent(data: TeacherDashboardData, onNavigateTo: (String) -> Unit, onLogout: () -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    var showMoreModules by remember { mutableStateOf(false) }

    val primaryModules = listOf(
        TModuleEntry("Attendance",   Icons.Filled.EventAvailable,  SoftGreen,  TintGreen,  "teacher_attendance"),
        TModuleEntry("Timetable",    Icons.Filled.CalendarMonth,   SoftBlue,   TintBlue,   "teacher_routine"),
        TModuleEntry("Assignments",  Icons.Filled.Assignment,      SoftOrange, TintOrange, "teacher_assignments"),
        TModuleEntry("Students",     Icons.Filled.Group,           SoftPurple, TintPurple, "teacher_students"),
        TModuleEntry("Exams",        Icons.Filled.MenuBook,        SoftRed,    TintRed,    ""),
        TModuleEntry("Results",      Icons.Filled.Stars,           SoftYellow, TintYellow, ""),
        TModuleEntry("Notes",        Icons.Filled.Note,            SoftPink,   TintPink,   ""),
        TModuleEntry("Reports",      Icons.Filled.Assessment,      SoftTeal,   TintTeal,   "")
    )

    val moreModules = listOf(
        TModuleEntry("Leave App",    Icons.Filled.EventBusy,       SoftRed,    TintRed,    ""),
        TModuleEntry("Homework",     Icons.Filled.Book,            SoftOrange, TintOrange, ""),
        TModuleEntry("Syllabus",     Icons.Filled.ListAlt,         SoftGreen,  TintGreen,  ""),
        TModuleEntry("ID Card",      Icons.Filled.CreditCard,      SoftPink,   TintPink,   "")
    )

    val initials = data.teacherInfo.name.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 40.dp)) {

        // ── Header + Stats Strip ─────────────────────────────────────────────
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .background(Brush.horizontalGradient(listOf(BlueStart, BlueEnd)))
                        .padding(top = 52.dp, bottom = 72.dp, start = 24.dp, end = 24.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(52.dp).clip(RoundedCornerShape(14.dp)).background(Color.White), contentAlignment = Alignment.Center) {
                                Text(initials.ifEmpty { "TC" }, color = BlueStart, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, fontFamily = ManropeFontFamily)
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text("Welcome back,", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp, fontFamily = ManropeFontFamily)
                                Text(data.teacherInfo.name, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold, fontFamily = ManropeFontFamily)
                                Text(data.teacherInfo.designation.ifEmpty { "Teacher Portal" }, color = Color.White.copy(alpha = 0.75f), fontSize = 12.sp, fontFamily = ManropeFontFamily)
                            }
                        }
                        Box(Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)).clickable { }, contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.NotificationsNone, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(22.dp))
                        }
                    }
                }

                // Floating stats card
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).align(Alignment.BottomCenter).offset(y = 44.dp).shadow(10.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(Modifier.fillMaxWidth().padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                        TStatItem(data.stats.assignedClasses.toString(), "CLASSES", TintBlue)
                        Box(Modifier.width(1.dp).height(36.dp).background(Color(0xFFE5E7EB)))
                        TStatItem(data.stats.assignedSubjects.toString(), "SUBJECTS", TintPurple)
                        Box(Modifier.width(1.dp).height(36.dp).background(Color(0xFFE5E7EB)))
                        TStatItem(data.todaySchedule.size.toString(), "TODAY", TintGreen)
                    }
                }
            }
            Spacer(Modifier.height(60.dp))
        }

        // ── Tab Bar ──────────────────────────────────────────────────────────
        item {
            Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp).clip(RoundedCornerShape(14.dp)).background(Color.White)) {
                listOf("Dashboard", "Updates").forEachIndexed { i, label ->
                    Box(Modifier.weight(1f).clickable { selectedTab = i }, contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(label, color = if (selectedTab == i) BlueStart else TextMuted,
                                fontFamily = ManropeFontFamily, fontWeight = if (selectedTab == i) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp, modifier = Modifier.padding(vertical = 14.dp))
                            if (selectedTab == i) Box(Modifier.fillMaxWidth().height(3.dp).background(BlueStart))
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }

        if (selectedTab == 0) {
            item {
                Text("Teaching Tools", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold,
                    fontSize = 16.sp, color = TextDark, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
                TModuleGrid(primaryModules, onNavigateTo)
                Spacer(Modifier.height(20.dp))
            }

            // Today's schedule
            if (data.todaySchedule.isNotEmpty()) {
                item {
                    Text("Today's Schedule", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold,
                        fontSize = 16.sp, color = TextDark, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
                    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        data.todaySchedule.forEach { schedule ->
                            Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp))) {
                                Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(Modifier.size(36.dp).clip(CircleShape).background(SoftPurple), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Filled.School, null, tint = TintPurple, modifier = Modifier.size(18.dp))
                                        }
                                        Spacer(Modifier.width(12.dp))
                                        Column {
                                            Text(schedule.className, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
                                            Text(schedule.subject, fontFamily = ManropeFontFamily, fontSize = 12.sp, color = TextMuted)
                                        }
                                    }
                                    Text("${schedule.startTime} – ${schedule.endTime}", fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TintBlue)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }

            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Surface(shape = RoundedCornerShape(24.dp), color = SoftBlue, onClick = { showMoreModules = !showMoreModules }) {
                        Row(Modifier.padding(horizontal = 22.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(if (showMoreModules) "View Less" else "View More", color = BlueStart, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(Modifier.width(4.dp))
                            Icon(if (showMoreModules) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null, tint = BlueStart, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                AnimatedVisibility(visible = showMoreModules, enter = expandVertically(), exit = shrinkVertically()) {
                    Column {
                        Text("More Modules", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold,
                            fontSize = 16.sp, color = TextDark, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
                        TModuleGrid(moreModules)
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }

        } else {
            item {
                Column(Modifier.fillMaxWidth().padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.Inbox, contentDescription = null, tint = Color(0xFFD1D5DB), modifier = Modifier.size(72.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("No new alerts", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                    Spacer(Modifier.height(8.dp))
                    Text("School operations are running smoothly.", fontFamily = ManropeFontFamily, fontSize = 13.sp, color = TextMuted, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 40.dp))
                }
            }
        }
    }
}

@Composable
fun TModuleGrid(modules: List<TModuleEntry>, onNavigateTo: (String) -> Unit = {}) {
    val rows = modules.chunked(4)
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        rows.forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                row.forEach { m -> TModuleIcon(title = m.title, icon = m.icon, bg = m.bg, tint = m.tint,
                    onClick = { if (m.route.isNotEmpty()) onNavigateTo(m.route) }) }
                repeat(4 - row.size) { Box(Modifier.width(72.dp)) }
            }
        }
    }
}

@Composable
fun TStatItem(value: String, label: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontFamily = ManropeFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = valueColor)
        Spacer(Modifier.height(2.dp))
        Text(label, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = TextMuted, letterSpacing = 0.5.sp)
    }
}

@Composable
fun TModuleIcon(title: String, icon: ImageVector, bg: Color, tint: Color, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(72.dp).clickable(onClick = onClick)) {
        Box(Modifier.size(62.dp).clip(RoundedCornerShape(16.dp)).background(bg), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = title, tint = tint, modifier = Modifier.size(28.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(title, fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 11.sp,
            color = TextDark, textAlign = TextAlign.Center, lineHeight = 14.sp, modifier = Modifier.fillMaxWidth())
    }
}

// Legacy compat
@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, gradient: List<Color>) {
    Card(modifier = modifier.shadow(3.dp, RoundedCornerShape(12.dp)), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Text(value, fontFamily = ManropeFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, color = TextDark)
            Text(title, fontFamily = ManropeFontFamily, fontSize = 12.sp, color = TextMuted)
        }
    }
}
