package com.shikshashila.app.ui.student.features

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.AttendanceRecord
import com.shikshashila.app.ui.student.FeatureState
import com.shikshashila.app.ui.student.StudentFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle as JTextStyle
import java.util.Locale

private val BlueStart = Color(0xFF2563EB)
private val BlueEnd   = Color(0xFF3B82F6)
private val BgColor   = Color(0xFFF8F9FA)
private val TextDark  = Color(0xFF111827)
private val TextMuted = Color(0xFF6B7280)
private val SoftGreen = Color(0xFFD1FAE5)
private val TintGreen = Color(0xFF059669)
private val SoftRed   = Color(0xFFFEE2E2)
private val TintRed   = Color(0xFFDC2626)
private val SoftBlue  = Color(0xFFDBEAFE)
private val TintBlue  = Color(0xFF2563EB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentAttendanceScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val attendanceState by viewModel.attendanceState.collectAsState()

    val today = LocalDate.now()
    var displayMonth by remember { mutableStateOf(today.monthValue) }
    var displayYear  by remember { mutableStateOf(today.year) }

    LaunchedEffect(Unit) {
        if (attendanceState is FeatureState.Idle) {
            viewModel.fetchAttendanceDetail(today.monthValue, today.year)
        }
    }

    // Re-fetch when month/year changes
    LaunchedEffect(displayMonth, displayYear) {
        viewModel.fetchAttendanceDetail(displayMonth, displayYear)
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Attendance",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueStart)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
        ) {
            when (val state = attendanceState) {
                is FeatureState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BlueStart
                    )
                }
                is FeatureState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = state.message,
                            color = TintRed,
                            fontFamily = ManropeFontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Button(
                            onClick = { viewModel.fetchAttendanceDetail(displayMonth, displayYear) },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily)
                        }
                    }
                }
                is FeatureState.Success -> {
                    val data = state.data
                    val recordMap: Map<String, Boolean> = data.records.associate { it.date to it.present }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // ── Summary Card ──────────────────────────────────────
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.horizontalGradient(listOf(BlueStart, BlueEnd)),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    AttendanceStat(
                                        label = "Total",
                                        value = data.summary.total.toString(),
                                        valueColor = Color.White,
                                        labelColor = Color.White.copy(alpha = 0.8f)
                                    )
                                    StatDivider()
                                    AttendanceStat(
                                        label = "Present",
                                        value = data.summary.present.toString(),
                                        valueColor = SoftGreen,
                                        labelColor = Color.White.copy(alpha = 0.8f)
                                    )
                                    StatDivider()
                                    AttendanceStat(
                                        label = "Absent",
                                        value = data.summary.absent.toString(),
                                        valueColor = SoftRed,
                                        labelColor = Color.White.copy(alpha = 0.8f)
                                    )
                                    StatDivider()
                                    AttendanceStat(
                                        label = "Score",
                                        value = "${"%.1f".format(data.summary.percentage)}%",
                                        valueColor = SoftBlue,
                                        labelColor = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }

                        // ── Month Navigator ───────────────────────────────────
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Month nav header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = {
                                        val ym = YearMonth.of(displayYear, displayMonth).minusMonths(1)
                                        displayMonth = ym.monthValue
                                        displayYear = ym.year
                                    }) {
                                        Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month", tint = BlueStart)
                                    }

                                    val monthName = YearMonth.of(displayYear, displayMonth)
                                        .month
                                        .getDisplayName(JTextStyle.FULL, Locale.getDefault())
                                    Text(
                                        text = "$monthName $displayYear",
                                        fontFamily = ManropeFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = TextDark
                                    )

                                    IconButton(onClick = {
                                        val ym = YearMonth.of(displayYear, displayMonth).plusMonths(1)
                                        displayMonth = ym.monthValue
                                        displayYear = ym.year
                                    }) {
                                        Icon(Icons.Default.ChevronRight, contentDescription = "Next Month", tint = BlueStart)
                                    }
                                }

                                Spacer(Modifier.height(8.dp))

                                // Day-of-week headers
                                val dayHeaders = listOf("S", "M", "T", "W", "T", "F", "S")
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                    dayHeaders.forEach { d ->
                                        Text(
                                            text = d,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.Center,
                                            fontFamily = ManropeFontFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 12.sp,
                                            color = TextMuted
                                        )
                                    }
                                }

                                Spacer(Modifier.height(8.dp))

                                // Calendar grid
                                val ym = YearMonth.of(displayYear, displayMonth)
                                val daysInMonth = ym.lengthOfMonth()
                                val firstDayOfWeek = ym.atDay(1).dayOfWeek.value % 7 // Sun=0

                                val cells = firstDayOfWeek + daysInMonth
                                val rows = (cells + 6) / 7

                                for (row in 0 until rows) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        for (col in 0..6) {
                                            val dayNum = row * 7 + col - firstDayOfWeek + 1
                                            if (dayNum < 1 || dayNum > daysInMonth) {
                                                Box(modifier = Modifier.weight(1f).size(36.dp))
                                            } else {
                                                val dateKey = "%04d-%02d-%02d".format(displayYear, displayMonth, dayNum)
                                                val present = recordMap[dateKey]
                                                CalendarDayCircle(
                                                    day = dayNum,
                                                    present = present,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                // Legend
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LegendDot(color = TintGreen)
                                    Text(" Present", fontFamily = ManropeFontFamily, fontSize = 12.sp, color = TextMuted)
                                    Spacer(Modifier.width(16.dp))
                                    LegendDot(color = TintRed)
                                    Text(" Absent", fontFamily = ManropeFontFamily, fontSize = 12.sp, color = TextMuted)
                                    Spacer(Modifier.width(16.dp))
                                    LegendDot(color = Color.Transparent, borderColor = TextMuted)
                                    Text(" No Record", fontFamily = ManropeFontFamily, fontSize = 12.sp, color = TextMuted)
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

@Composable
private fun AttendanceStat(
    label: String,
    value: String,
    valueColor: Color,
    labelColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            color = valueColor
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = labelColor
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Color.White.copy(alpha = 0.3f))
    )
}

@Composable
private fun CalendarDayCircle(
    day: Int,
    present: Boolean?,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = when (present) {
            true  -> TintGreen
            false -> TintRed
            null  -> Color.Transparent
        },
        animationSpec = tween(300),
        label = "dayBg"
    )
    val textColor = when (present) {
        true  -> Color.White
        false -> Color.White
        null  -> Color(0xFF9CA3AF)
    }

    Box(
        modifier = modifier
            .padding(2.dp)
            .size(34.dp)
            .clip(CircleShape)
            .background(bgColor)
            .then(
                if (present == null)
                    Modifier.border(1.dp, Color(0xFFD1D5DB), CircleShape)
                else
                    Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = textColor
        )
    }
}

@Composable
private fun LegendDot(color: Color, borderColor: Color = Color.Transparent) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (borderColor != Color.Transparent)
                    Modifier.border(1.dp, borderColor, CircleShape)
                else Modifier
            )
    )
}
