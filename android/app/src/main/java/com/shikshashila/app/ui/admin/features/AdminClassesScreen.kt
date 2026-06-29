package com.shikshashila.app.ui.admin.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.shikshashila.app.data.model.AdminClass
import com.shikshashila.app.data.model.ClassSection
import com.shikshashila.app.ui.admin.AdminFeatureState
import com.shikshashila.app.ui.admin.AdminFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

// ─── Design tokens ────────────────────────────────────────────────────────────
private val BlueStart  = Color(0xFF2563EB)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)
private val SoftBlue   = Color(0xFFDBEAFE)
private val TintBlue   = Color(0xFF2563EB)
private val SoftGreen  = Color(0xFFD1FAE5)
private val TintGreen  = Color(0xFF059669)
private val SoftPurple = Color(0xFFEDE9FE)
private val TintPurple = Color(0xFF7C3AED)

// ─── Screen ───────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminClassesScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminFeatureViewModel = hiltViewModel()
) {
    val state by viewModel.classesState.collectAsState()

    LaunchedEffect(Unit) {
        if (state is AdminFeatureState.Idle) {
            viewModel.fetchClasses()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Classes",
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
                            onClick = { viewModel.fetchClasses() },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily, color = Color.White)
                        }
                    }
                }

                is AdminFeatureState.Success -> {
                    val data = s.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Header count
                        item {
                            Text(
                                text = "${data.total} class${if (data.total != 1) "es" else ""} total",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        // Class cards
                        items(data.classes, key = { it.classId }) { adminClass ->
                            ExpandableClassCard(adminClass = adminClass)
                        }

                        if (data.classes.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No classes found",
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
            }
        }
    }
}

// ─── Expandable Class Card ────────────────────────────────────────────────────

@Composable
private fun ExpandableClassCard(adminClass: AdminClass) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Card header — clickable to expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Class label bubble
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SoftBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = adminClass.className
                            .filter { it.isLetterOrDigit() }
                            .take(3),
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        color = TintBlue
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = adminClass.className,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = TextDark
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        InfoChip(
                            label = "${adminClass.sectionCount} Section${if (adminClass.sectionCount != 1) "s" else ""}",
                            bgColor = SoftPurple,
                            textColor = TintPurple
                        )
                        InfoChip(
                            label = "${adminClass.studentCount} Student${if (adminClass.studentCount != 1) "s" else ""}",
                            bgColor = SoftGreen,
                            textColor = TintGreen
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = TextMuted,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable sections list
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F9FA))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (adminClass.sections.isEmpty()) {
                        Text(
                            text = "No sections defined",
                            fontFamily = ManropeFontFamily,
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    } else {
                        adminClass.sections.forEach { section ->
                            SectionRow(section = section)
                        }
                    }
                }
            }
        }
    }
}

// ─── Section Row ─────────────────────────────────────────────────────────────

@Composable
private fun SectionRow(section: ClassSection) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(TintBlue)
        )
        Text(
            text = section.sectionName,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = TextDark
        )
    }
}

// ─── Info Chip ────────────────────────────────────────────────────────────────

@Composable
private fun InfoChip(label: String, bgColor: Color, textColor: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
