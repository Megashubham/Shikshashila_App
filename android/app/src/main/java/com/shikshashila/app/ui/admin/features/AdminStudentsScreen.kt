package com.shikshashila.app.ui.admin.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.AdminStudent
import com.shikshashila.app.ui.admin.AdminFeatureState
import com.shikshashila.app.ui.admin.AdminFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

// ─── Design tokens ────────────────────────────────────────────────────────────
private val BlueStart   = Color(0xFF2563EB)
private val BgColor     = Color(0xFFF8F9FA)
private val TextDark    = Color(0xFF111827)
private val TextMuted   = Color(0xFF6B7280)
private val SoftBlue    = Color(0xFFDBEAFE)
private val TintBlue    = Color(0xFF2563EB)
private val SoftGreen   = Color(0xFFD1FAE5)
private val TintGreen   = Color(0xFF059669)
private val SoftPurple  = Color(0xFFEDE9FE)
private val TintPurple  = Color(0xFF7C3AED)

// ─── Screen ───────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStudentsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminFeatureViewModel = hiltViewModel()
) {
    val state by viewModel.studentsState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Initial load
    LaunchedEffect(Unit) {
        if (state is AdminFeatureState.Idle) {
            viewModel.fetchStudents()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Students",
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueStart
                )
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
                            imageVector = Icons.Default.Person,
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
                            onClick = { viewModel.fetchStudents(searchQuery) },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text(
                                "Retry",
                                fontFamily = ManropeFontFamily,
                                color = Color.White
                            )
                        }
                    }
                }

                is AdminFeatureState.Success -> {
                    val data = s.data
                    val filtered = remember(searchQuery, data.students) {
                        if (searchQuery.isBlank()) data.students
                        else data.students.filter { student ->
                            student.name.contains(searchQuery, ignoreCase = true) ||
                                    student.registrationNo.contains(searchQuery, ignoreCase = true) ||
                                    student.className.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Search bar
                        item {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = {
                                    Text(
                                        "Search by name, reg. no. or class…",
                                        fontFamily = ManropeFontFamily,
                                        color = TextMuted,
                                        fontSize = 13.sp
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                        tint = TextMuted
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BlueStart,
                                    unfocusedBorderColor = Color(0xFFE5E7EB),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = { focusManager.clearFocus() }
                                )
                            )
                        }

                        // Subtitle count
                        item {
                            Text(
                                text = "${filtered.size} of ${data.total} students",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        // Student cards
                        items(filtered, key = { it.studentId }) { student ->
                            StudentCard(student = student)
                        }

                        if (filtered.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No students found",
                                        fontFamily = ManropeFontFamily,
                                        color = TextMuted,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        // Bottom spacing
                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
}

// ─── Student Card ─────────────────────────────────────────────────────────────

@Composable
private fun StudentCard(student: AdminStudent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Avatar circle with initials
            InitialsAvatar(
                name = student.name,
                size = 46,
                bgColor = SoftBlue,
                textColor = TintBlue
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${student.className} – ${student.sectionName}",
                    fontFamily = ManropeFontFamily,
                    fontSize = 13.sp,
                    color = TextMuted
                )
                Text(
                    text = "Reg: ${student.registrationNo}",
                    fontFamily = ManropeFontFamily,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            // Gender chip
            val (chipBg, chipText) = when (student.gender.lowercase()) {
                "female", "f" -> SoftPurple to TintPurple
                else -> SoftBlue to TintBlue
            }
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = chipBg
            ) {
                Text(
                    text = student.gender.replaceFirstChar { it.uppercase() },
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = chipText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// ─── Shared composable ────────────────────────────────────────────────────────

@Composable
internal fun InitialsAvatar(
    name: String,
    size: Int,
    bgColor: Color,
    textColor: Color
) {
    val initials = name
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercaseChar().toString() }

    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (size * 0.35f).sp,
            color = textColor
        )
    }
}
