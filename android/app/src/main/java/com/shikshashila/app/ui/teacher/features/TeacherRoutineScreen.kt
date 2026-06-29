package com.shikshashila.app.ui.teacher.features

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.shikshashila.app.data.model.TRoutineSlot
import com.shikshashila.app.ui.teacher.TeacherFeatureState
import com.shikshashila.app.ui.teacher.TeacherFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

private val BlueStart = Color(0xFF2563EB)
private val BlueEnd   = Color(0xFF3B82F6)
private val BgColor   = Color(0xFFF8F9FA)
private val TextDark  = Color(0xFF111827)
private val TextMuted = Color(0xFF6B7280)
private val TintRed   = Color(0xFFDC2626)
private val SoftBlue  = Color(0xFFDBEAFE)

private val DAYS = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
private val DAY_KEYS = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherRoutineScreen(
    viewModel: TeacherFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val routineState by viewModel.routineState.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        if (routineState is TeacherFeatureState.Idle) {
            viewModel.fetchTeacherRoutine()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Timetable",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BlueStart)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
        ) {
            // ── Day Selector TabRow ───────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(BlueStart, BlueEnd)))
            ) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    edgePadding = 8.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color.White,
                            height = 3.dp
                        )
                    }
                ) {
                    DAYS.forEachIndexed { index, day ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = day,
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (selectedTabIndex == index) Color.White else Color.White.copy(alpha = 0.65f)
                                )
                            }
                        )
                    }
                }
            }

            // ── Content ───────────────────────────────────────────────────────
            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = routineState) {
                    is TeacherFeatureState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = BlueStart
                        )
                    }
                    is TeacherFeatureState.Error -> {
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
                                onClick = { viewModel.fetchTeacherRoutine() },
                                colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                            ) {
                                Text("Retry", fontFamily = ManropeFontFamily)
                            }
                        }
                    }
                    is TeacherFeatureState.Success -> {
                        val routineMap = state.data.routine
                        val selectedDayKey = DAY_KEYS[selectedTabIndex]
                        val slots = routineMap[selectedDayKey] ?: emptyList()

                        if (slots.isEmpty()) {
                            EmptyDayState(
                                day = DAYS[selectedTabIndex],
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                item {
                                    Text(
                                        text = "${DAYS[selectedTabIndex]} — ${slots.size} class${if (slots.size != 1) "es" else ""}",
                                        fontFamily = ManropeFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        color = TextMuted
                                    )
                                }
                                items(slots) { slot ->
                                    RoutineSlotCard(slot = slot)
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun RoutineSlotCard(slot: TRoutineSlot) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time column
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp))
                    .background(SoftBlue)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = slot.startTime,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BlueStart
                    )
                    Text(
                        text = "—",
                        fontFamily = ManropeFontFamily,
                        fontSize = 11.sp,
                        color = BlueStart.copy(alpha = 0.5f)
                    )
                    Text(
                        text = slot.endTime,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BlueStart
                    )
                }
            }

            // Vertical divider
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(64.dp)
                    .background(BlueStart.copy(alpha = 0.15f))
            )

            // Subject + class info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Text(
                    text = slot.subject,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SoftBlue
                ) {
                    Text(
                        text = slot.className,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = BlueStart,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyDayState(day: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "No classes on $day",
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark
        )
        Text(
            text = "Enjoy your free time!",
            fontFamily = ManropeFontFamily,
            fontSize = 14.sp,
            color = TextMuted
        )
    }
}
