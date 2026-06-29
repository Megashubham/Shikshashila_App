package com.shikshashila.app.ui.admin.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.AdminStaff
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
private val SoftYellow = Color(0xFFFEF3C7)
private val TintYellow = Color(0xFFD97706)
private val SoftRed    = Color(0xFFFEE2E2)
private val TintRed    = Color(0xFFDC2626)

// ─── Filter options ───────────────────────────────────────────────────────────

private enum class StaffFilter(val label: String, val apiValue: String?) {
    ALL("All", null),
    TEACHING("Teaching", "teaching"),
    NON_TEACHING("Non-Teaching", "non-teaching")
}

// ─── Screen ───────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStaffScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminFeatureViewModel = hiltViewModel()
) {
    val state by viewModel.staffState.collectAsState()
    var selectedFilter by remember { mutableStateOf(StaffFilter.ALL) }

    // Initial load
    LaunchedEffect(Unit) {
        if (state is AdminFeatureState.Idle) {
            viewModel.fetchStaff()
        }
    }

    // Re-fetch when filter changes
    LaunchedEffect(selectedFilter) {
        viewModel.fetchStaff(selectedFilter.apiValue)
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Staff Management",
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
                            onClick = { viewModel.fetchStaff(selectedFilter.apiValue) },
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

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Filter chips row
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                items(StaffFilter.values()) { filter ->
                                    val selected = selectedFilter == filter
                                    FilterChip(
                                        selected = selected,
                                        onClick = { selectedFilter = filter },
                                        label = {
                                            Text(
                                                text = filter.label,
                                                fontFamily = ManropeFontFamily,
                                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                                fontSize = 13.sp
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = BlueStart,
                                            selectedLabelColor = Color.White,
                                            containerColor = Color.White,
                                            labelColor = TextDark
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = selected,
                                            selectedBorderColor = Color.Transparent,
                                            borderColor = Color(0xFFE5E7EB)
                                        )
                                    )
                                }
                            }
                        }

                        // Subtitle
                        item {
                            Text(
                                text = "${data.staff.size} staff member${if (data.staff.size != 1) "s" else ""}",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        // Staff cards
                        items(data.staff, key = { it.staffId }) { staff ->
                            StaffCard(staff = staff)
                        }

                        if (data.staff.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No staff found",
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

// ─── Staff Card ───────────────────────────────────────────────────────────────

@Composable
private fun StaffCard(staff: AdminStaff) {
    val (typeBg, typeColor) = when (staff.type.lowercase()) {
        "teaching" -> SoftGreen to TintGreen
        else -> SoftYellow to TintYellow
    }
    val avatarBg = when (staff.type.lowercase()) {
        "teaching" -> SoftGreen
        else -> SoftYellow
    }
    val avatarText = when (staff.type.lowercase()) {
        "teaching" -> TintGreen
        else -> TintYellow
    }

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
            // Initials avatar
            InitialsAvatar(
                name = staff.name,
                size = 48,
                bgColor = avatarBg,
                textColor = avatarText
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = staff.name,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = staff.designation,
                    fontFamily = ManropeFontFamily,
                    fontSize = 13.sp,
                    color = TextMuted
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = staff.email,
                        fontFamily = ManropeFontFamily,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            // Type badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = typeBg
            ) {
                Text(
                    text = staff.type.split("-").joinToString(" ") {
                        it.replaceFirstChar { c -> c.uppercase() }
                    },
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = typeColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}
