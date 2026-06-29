package com.shikshashila.app.ui.admin

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
import com.shikshashila.app.data.model.AdminDashboardData
import com.shikshashila.app.ui.theme.ManropeFontFamily

// --- Custom Colors based on the mockup ---
private val BlueHeaderStart = Color(0xFF2563EB)
private val BlueHeaderEnd = Color(0xFF3B82F6)
private val BgColor = Color(0xFFF8F9FA)
private val TextDark = Color(0xFF0F172A)
private val TextMuted = Color(0xFF6B7280)

// Soft Icon Backgrounds
private val SoftPurple = Color(0xFFEDE9FE)
private val SoftPink = Color(0xFFFCE7F3)
private val SoftGreen = Color(0xFFD1FAE5)
private val SoftYellow = Color(0xFFFEF3C7)
private val SoftBlue = Color(0xFFDBEAFE)
private val SoftRed = Color(0xFFFEE2E2)
private val SoftTeal = Color(0xFFCCFBF1)

// Icon Tints
private val TintPurple = Color(0xFF7C3AED)
private val TintPink = Color(0xFFDB2777)
private val TintGreen = Color(0xFF059669)
private val TintYellow = Color(0xFFD97706)
private val TintBlue = Color(0xFF2563EB)
private val TintRed = Color(0xFFDC2626)
private val TintTeal = Color(0xFF0D9488)

@Composable
fun AdminDashboardScreen(
    viewModel: AdminViewModel = hiltViewModel(),
    onNavigateTo: (String) -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.dashboardState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        when (uiState) {
            is AdminState.Loading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = BlueHeaderStart)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Loading dashboard...",
                        color = TextMuted,
                        fontFamily = ManropeFontFamily,
                        fontSize = 14.sp
                    )
                }
            }
            is AdminState.Error -> {
                val error = (uiState as AdminState.Error).message
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEE2E2)
                    ) {
                        Text(
                            text = error,
                            color = Color(0xFFDC2626),
                            fontSize = 14.sp,
                            fontFamily = ManropeFontFamily,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.fetchDashboard() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TextDark)
                    ) {
                        Text("Retry", fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            is AdminState.Success -> {
                val data = (uiState as AdminState.Success).data
                AdminDashboardContent(data, onNavigateTo, onLogout)
            }
            else -> {}
        }
    }
}

@Composable
fun AdminDashboardContent(
    data: AdminDashboardData,
    onNavigateTo: (String) -> Unit,
    onLogout: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // --- Header Section ---
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Blue Gradient Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .background(Brush.linearGradient(listOf(BlueHeaderStart, BlueHeaderEnd)))
                        .padding(top = 48.dp, bottom = 70.dp, start = 24.dp, end = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Avatar Box
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "DS",
                                    color = BlueHeaderStart,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = ManropeFontFamily
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Welcome back,",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 13.sp,
                                    fontFamily = ManropeFontFamily
                                )
                                Text(
                                    text = data.adminInfo.schoolName.ifEmpty { "DPS International School" },
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = ManropeFontFamily
                                )
                                Text(
                                    text = "School Admin Portal",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    fontFamily = ManropeFontFamily
                                )
                            }
                        }
                        // Logout / Notification Icon
                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Stats Strip Overlapping the header
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 40.dp)
                        .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color(0x1A000000)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatItem(data.stats.totalStudents.toString(), "STUDENTS", TintBlue)
                        Divider(
                            modifier = Modifier
                                .height(40.dp)
                                .width(1.dp),
                            color = Color(0xFFF3F4F6)
                        )
                        StatItem(data.stats.totalStaff.toString(), "STAFF", TintBlue)
                        Divider(
                            modifier = Modifier
                                .height(40.dp)
                                .width(1.dp),
                            color = Color(0xFFF3F4F6)
                        )
                        // Note: Format the currency correctly later if needed
                        StatItem("₹56,000", "REVENUE", TintGreen)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(60.dp))
        }

        // --- Tabs ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Dashboard",
                            color = TintBlue,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                                .background(TintBlue)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Updates",
                        color = TextMuted,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Academics & Operations ---
        item {
            Text(
                text = "Academics & Operations",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DashboardModuleIcon(
                        title = "Students", icon = Icons.Filled.Group,
                        bg = SoftPurple, tint = TintPurple
                    )
                    DashboardModuleIcon(
                        title = "Staff Mgmt", icon = Icons.Filled.Badge,
                        bg = SoftPink, tint = TintPink
                    )
                    DashboardModuleIcon(
                        title = "Attendance", icon = Icons.Filled.EventAvailable,
                        bg = SoftGreen, tint = TintGreen
                    )
                    DashboardModuleIcon(
                        title = "Fees / Dues", icon = Icons.Filled.AccountBalanceWallet,
                        bg = SoftYellow, tint = TintYellow
                    )
                }
                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DashboardModuleIcon(
                        title = "Classes", icon = Icons.Filled.GridView,
                        bg = SoftBlue, tint = TintBlue
                    )
                    DashboardModuleIcon(
                        title = "Timetable", icon = Icons.Filled.CalendarMonth,
                        bg = SoftTeal, tint = TintTeal
                    )
                    DashboardModuleIcon(
                        title = "Exams", icon = Icons.Filled.MenuBook,
                        bg = SoftPurple, tint = TintPurple
                    )
                    DashboardModuleIcon(
                        title = "Reports", icon = Icons.Filled.Assessment,
                        bg = SoftBlue, tint = TintBlue
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Communication & Tools ---
        item {
            Text(
                text = "Communication & Tools",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DashboardModuleIcon(
                        title = "Circulars", icon = Icons.Filled.Notifications,
                        bg = SoftYellow, tint = TintYellow
                    )
                    DashboardModuleIcon(
                        title = "Events", icon = Icons.Filled.Event,
                        bg = SoftPink, tint = TintPink
                    )
                    DashboardModuleIcon(
                        title = "SMS / Alert", icon = Icons.Filled.Sms,
                        bg = SoftGreen, tint = TintGreen
                    )
                    DashboardModuleIcon(
                        title = "Settings", icon = Icons.Filled.Settings,
                        bg = SoftBlue, tint = TintBlue
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // --- View More Button ---
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = SoftBlue,
                    onClick = { /* View More */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "View More",
                            color = TintBlue,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TintBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = valueColor
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = TextMuted,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun DashboardModuleIcon(
    title: String,
    icon: ImageVector,
    bg: Color,
    tint: Color,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(76.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = tint,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
