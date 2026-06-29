package com.shikshashila.app.ui.teacher.features

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Group
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
import com.shikshashila.app.data.model.TeacherClass
import com.shikshashila.app.data.model.TeacherStudentItem
import com.shikshashila.app.ui.teacher.TeacherFeatureState
import com.shikshashila.app.ui.teacher.TeacherFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

private val BlueStart  = Color(0xFF2563EB)
private val BlueEnd    = Color(0xFF3B82F6)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)
private val TintRed    = Color(0xFFDC2626)
private val PurpleBg   = Color(0xFFEDE9FE)
private val PurpleTint = Color(0xFF7C3AED)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherStudentsScreen(
    viewModel: TeacherFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val classesState by viewModel.classesState.collectAsState()
    val studentsListState by viewModel.studentsListState.collectAsState()

    var selectedClass by remember { mutableStateOf<TeacherClass?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (classesState is TeacherFeatureState.Idle) {
            viewModel.fetchClasses()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Students",
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
                actions = {
                    // Student count badge
                    if (studentsListState is TeacherFeatureState.Success) {
                        val count = (studentsListState as TeacherFeatureState.Success).data.students.size
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.White.copy(alpha = 0.25f),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Text(
                                text = "$count",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
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
            // ── Class/Section Selector ────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(BlueStart, BlueEnd)))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                when (val cs = classesState) {
                    is TeacherFeatureState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp).align(Alignment.Center),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    }
                    is TeacherFeatureState.Success -> {
                        val classes = cs.data.classes

                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = selectedClass?.display ?: "Select a Class & Section",
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.ExpandMore,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.White.copy(alpha = 0.15f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.15f),
                                    focusedBorderColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
                                ),
                                textStyle = LocalTextStyle.current.copy(
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false }
                            ) {
                                classes.forEach { tc ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = tc.display,
                                                fontFamily = ManropeFontFamily,
                                                fontWeight = FontWeight.Medium
                                            )
                                        },
                                        onClick = {
                                            selectedClass = tc
                                            dropdownExpanded = false
                                            viewModel.fetchStudentsList(tc.classId, tc.sectionId)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    is TeacherFeatureState.Error -> {
                        Text(
                            text = "Failed to load classes",
                            color = Color.White,
                            fontFamily = ManropeFontFamily,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {}
                }
            }

            // ── Students List ─────────────────────────────────────────────────
            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = studentsListState) {
                    is TeacherFeatureState.Idle -> {
                        if (selectedClass == null) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = Color(0xFFD1D5DB),
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = "Select a class to view students",
                                    fontFamily = ManropeFontFamily,
                                    fontSize = 15.sp,
                                    color = TextMuted,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                            }
                        }
                    }
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
                                onClick = {
                                    selectedClass?.let {
                                        viewModel.fetchStudentsList(it.classId, it.sectionId)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                            ) {
                                Text("Retry", fontFamily = ManropeFontFamily)
                            }
                        }
                    }
                    is TeacherFeatureState.Success -> {
                        val students = state.data.students

                        if (students.isEmpty()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = Color(0xFFD1D5DB),
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = "No students in this class",
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = TextDark
                                )
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(students, key = { it.studentId }) { student ->
                                    AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn() + slideInVertically()
                                    ) {
                                        StudentCard(student = student)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentCard(student: TeacherStudentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Initials Avatar
            val initials = student.name
                .split(" ")
                .take(2)
                .mapNotNull { it.firstOrNull()?.uppercase() }
                .joinToString("")

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(PurpleBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials.ifEmpty { "?" },
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PurpleTint
                )
            }

            // Name + Reg No
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Reg: ${student.registrationNo}",
                    fontFamily = ManropeFontFamily,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }
    }
}
