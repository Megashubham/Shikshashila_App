package com.shikshashila.app.ui.admin

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
import com.shikshashila.app.data.model.AdminDashboardData
import com.shikshashila.app.ui.theme.ManropeFontFamily

// ── Website colors ─────────────────────────────────────────────────────────
private val Purple    = Color(0xFF8B5CF6)
private val Pink      = Color(0xFFEC4899)
private val Dark      = Color(0xFF374151)
private val Muted     = Color(0xFF6B7280)
private val BgFrom    = Color(0xFFDFE9F3)
private val BgTo      = Color(0xFFFFFFFF)
private val CardWhite = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: AdminViewModel = hiltViewModel(),
    onNavigateTo: (String) -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.dashboardState.collectAsState()

    // Same gradient background as login page
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
                // White top bar matching website header style
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text       = "🎓",
                                fontSize   = 20.sp
                            )
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
                        containerColor          = Color.White,
                        titleContentColor       = Color(0xFF0F172A),
                        actionIconContentColor  = Dark
                    ),
                    actions = {
                        IconButton(onClick = {
                            viewModel.logout()
                            onLogout()
                        }) {
                            Icon(
                                Icons.Default.ExitToApp,
                                contentDescription = "Logout",
                                tint = Dark
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when (uiState) {
                    is AdminState.Loading -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = Purple)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Loading dashboard...",
                                color = Muted,
                                fontFamily = ManropeFontFamily,
                                fontSize = 14.sp
                            )
                        }
                    }

                    is AdminState.Error -> {
                        val error = (uiState as AdminState.Error).message
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape  = RoundedCornerShape(12.dp),
                                color  = Color(0xFFFEE2E2)
                            ) {
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

                    is AdminState.Success -> {
                        val data = (uiState as AdminState.Success).data
                        AdminDashboardContent(data, onNavigateTo)
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun AdminDashboardContent(
    data        : AdminDashboardData,
    onNavigateTo: (String) -> Unit
) {
    LazyColumn(
        modifier            = Modifier.fillMaxSize(),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Welcome card ─────────────────────────────────────────────────
        item {
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar circle with gradient
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(Purple, Pink))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector      = Icons.Default.Person,
                            contentDescription = null,
                            tint             = Color.White,
                            modifier         = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text       = "Welcome, ${data.adminInfo.name}",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp,
                            color      = Color(0xFF0F172A)
                        )
                        Text(
                            text       = "Administrator · ${data.adminInfo.schoolName}",
                            fontFamily = ManropeFontFamily,
                            fontSize   = 13.sp,
                            color      = Muted
                        )
                    }
                }
            }
        }

        // ── Section label ─────────────────────────────────────────────────
        item {
            Text(
                text       = "School Statistics",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Color(0xFF0F172A),
                modifier   = Modifier.padding(top = 4.dp)
            )
        }

        // ── Stat cards grid ───────────────────────────────────────────────
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Total Students",
                    value    = data.stats.totalStudents.toString(),
                    icon     = Icons.Default.People,
                    gradient = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
                )
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Total Staff",
                    value    = data.stats.totalStaff.toString(),
                    icon     = Icons.Default.Group,
                    gradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
                )
            }
        }
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Teaching Staff",
                    value    = data.stats.teachingStaff.toString(),
                    icon     = Icons.Default.School,
                    gradient = listOf(Color(0xFF10B981), Color(0xFF34D399))
                )
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title    = "Non-Teaching",
                    value    = data.stats.nonTeachingStaff.toString(),
                    icon     = Icons.Default.Work,
                    gradient = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
                )
            }
        }

        // ── Section label ─────────────────────────────────────────────────
        item {
            Text(
                text       = "Management Modules",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Color(0xFF0F172A),
                modifier   = Modifier.padding(top = 4.dp)
            )
        }

        // ── Module buttons ────────────────────────────────────────────────
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ModuleButton(
                    modifier = Modifier.weight(1f),
                    title    = "Fees & Dues",
                    icon     = Icons.Default.AccountBalance,
                    color    = Purple,
                    onClick  = { onNavigateTo("admin_fees_report") }
                )
                ModuleButton(
                    modifier = Modifier.weight(1f),
                    title    = "Staff Directory",
                    icon     = Icons.Default.People,
                    color    = Color(0xFF3B82F6),
                    onClick  = {}
                )
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
fun AdminStatCard(
    modifier : Modifier = Modifier,
    title    : String,
    value    : String,
    icon     : ImageVector,
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
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector      = icon,
                    contentDescription = null,
                    tint             = Color.White,
                    modifier         = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text       = value,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize   = 28.sp,
                color      = Color(0xFF0F172A)
            )
            Text(
                text       = title,
                fontFamily = ManropeFontFamily,
                fontSize   = 12.sp,
                color      = Muted,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ModuleButton(
    modifier: Modifier = Modifier,
    title   : String,
    icon    : ImageVector,
    color   : Color,
    onClick : () -> Unit
) {
    Card(
        modifier  = modifier.shadow(3.dp, RoundedCornerShape(12.dp)),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        onClick   = onClick
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
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = color,
                    modifier           = Modifier.size(22.dp)
                )
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
