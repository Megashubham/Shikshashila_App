package com.shikshashila.app.ui.admin.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.AdminAttendanceResponse
import com.shikshashila.app.data.model.ClassAttendance
import com.shikshashila.app.ui.admin.AdminFeatureState
import com.shikshashila.app.ui.admin.AdminFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ─── Design tokens ────────────────────────────────────────────────────────────
private val BlueStart  = Color(0xFF2563EB)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)
private val SoftBlue   = Color(0xFFDBEAFE)
private val TintBlue   = Color(0xFF2563EB)
private val SoftGreen  = Color(0xFFD1FAE5)
private val TintGreen  = Color(0xFF059669)
private val SoftRed    = Color(0xFFFEE2E2)
private val TintRed    = Color(0xFFDC2626)
private val SoftYellow = Color(0xFFFEF3C7)
private val TintYellow = Color(0xFFD97706)

// ─── Screen ───────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAttendanceScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminFeatureViewModel = hiltViewModel()
) {
    val state by viewModel.attendanceState.collectAsState()

    LaunchedEffect(Unit) {
        if (state is AdminFeatureState.Idle) {
            viewModel.fetchAttendance()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Attendance Overview",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueStart)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val s = state) {
                is AdminFeatureState.Idle, is AdminFeatureState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BlueStart
                    )
                }

                is AdminFeatureState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = s.message,
                            fontFamily = ManropeFontFamily,
                            color = TextMuted,
                            fontSize = 14.sp
                        )
                        Button(
                            onClick = { viewModel.fetchAttendance() },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily, color = Color.White)
                        }
                    }
                }

                is AdminFeatureState.Success -> {
                    AttendanceContent(data = s.data)
                }
            }
        }
    }
}

// ─── Content ──────────────────────────────────────────────────────────────────

@Composable
private fun AttendanceContent(data: AdminAttendanceResponse) {
    // Format date header: "Today, 29 Jun 2026"
    val dateLabel = remember(data.date) {
        runCatching {
            val inFmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outFmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            "Today, " + outFmt.format(inFmt.parse(data.date) ?: Date())
        }.getOrDefault("Today")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Date header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = TintBlue,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = dateLabel,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextDark
                )
            }
        }

        // Summary stats card
        item {
            SummaryStatsCard(data = data)
        }

        // Section title
        item {
            Text(
                text = "Class-wise Breakdown",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextDark,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Per-class attendance cards
        items(data.byClass, key = { it.className }) { classAtt ->
            ClassAttendanceCard(classAtt = classAtt)
        }

        if (data.byClass.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No class attendance data available",
                        fontFamily = ManropeFontFamily,
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

// ─── Summary Stats Card ───────────────────────────────────────────────────────

@Composable
private fun SummaryStatsCard(data: AdminAttendanceResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Today's Summary",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextDark
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatBubble(
                    label = "Enrolled",
                    value = data.enrolled.toString(),
                    bgColor = SoftBlue,
                    textColor = TintBlue
                )
                StatBubble(
                    label = "Marked",
                    value = data.summary.totalMarked.toString(),
                    bgColor = SoftYellow,
                    textColor = TintYellow
                )
                StatBubble(
                    label = "Present",
                    value = data.summary.present.toString(),
                    bgColor = SoftGreen,
                    textColor = TintGreen
                )
                StatBubble(
                    label = "Absent",
                    value = data.summary.absent.toString(),
                    bgColor = SoftRed,
                    textColor = TintRed
                )
            }
        }
    }
}

@Composable
private fun StatBubble(
    label: String,
    value: String,
    bgColor: Color,
    textColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = textColor
            )
        }
        Text(
            text = label,
            fontFamily = ManropeFontFamily,
            fontSize = 11.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─── Per-class Card ───────────────────────────────────────────────────────────

@Composable
private fun ClassAttendanceCard(classAtt: ClassAttendance) {
    val percentage = classAtt.percentage.coerceIn(0.0, 100.0)
    val barColor = when {
        percentage >= 85.0 -> TintGreen
        percentage >= 65.0 -> TintYellow
        else -> TintRed
    }
    val barBgColor = when {
        percentage >= 85.0 -> SoftGreen
        percentage >= 65.0 -> SoftYellow
        else -> SoftRed
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = classAtt.className,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${classAtt.present}/${classAtt.total}",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }

            Spacer(Modifier.height(8.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(barBgColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = (percentage / 100.0).toFloat())
                        .clip(RoundedCornerShape(4.dp))
                        .background(barColor)
                )
            }

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${classAtt.absent} absent",
                    fontFamily = ManropeFontFamily,
                    fontSize = 12.sp,
                    color = TintRed
                )
                Text(
                    text = "${"%.1f".format(percentage)}%",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = barColor
                )
            }
        }
    }
}
