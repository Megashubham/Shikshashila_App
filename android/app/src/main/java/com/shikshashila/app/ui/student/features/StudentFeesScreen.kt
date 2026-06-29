package com.shikshashila.app.ui.student.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ReceiptLong
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
import com.shikshashila.app.data.model.FeePayment
import com.shikshashila.app.ui.student.FeatureState
import com.shikshashila.app.ui.student.StudentFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

private val BlueStart  = Color(0xFF2563EB)
private val BlueEnd    = Color(0xFF3B82F6)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)
private val SoftGreen  = Color(0xFFD1FAE5)
private val TintGreen  = Color(0xFF059669)
private val TintRed    = Color(0xFFDC2626)
private val SoftYellow = Color(0xFFFEF3C7)
private val TintYellow = Color(0xFFD97706)
private val SoftBlue   = Color(0xFFDBEAFE)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFeesScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val feesState by viewModel.feesState.collectAsState()

    LaunchedEffect(Unit) {
        if (feesState is FeatureState.Idle) {
            viewModel.fetchFees()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Fee Payments",
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
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
        ) {
            when (val state = feesState) {
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
                            onClick = { viewModel.fetchFees() },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily)
                        }
                    }
                }
                is FeatureState.Success -> {
                    val data = state.data

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // ── Total Paid Header Card ────────────────────────────
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Brush.horizontalGradient(listOf(BlueStart, BlueEnd)),
                                            RoundedCornerShape(20.dp)
                                        )
                                        .padding(28.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Total Amount Paid",
                                            fontFamily = ManropeFontFamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            color = Color.White.copy(alpha = 0.85f)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            text = "₹${"%,.2f".format(data.totalPaid)}",
                                            fontFamily = ManropeFontFamily,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 38.sp,
                                            color = SoftGreen
                                        )
                                        Spacer(Modifier.height(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(50),
                                            color = Color.White.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = "${data.payments.size} payment${if (data.payments.size != 1) "s" else ""} recorded",
                                                fontFamily = ManropeFontFamily,
                                                fontSize = 12.sp,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // ── Payment History Label ─────────────────────────────
                        if (data.payments.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Payment History",
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = TextDark,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        // ── Payment Cards ─────────────────────────────────────
                        if (data.payments.isEmpty()) {
                            item { EmptyFeesState() }
                        } else {
                            items(data.payments) { payment ->
                                FeePaymentCard(payment = payment)
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
private fun FeePaymentCard(payment: FeePayment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Receipt #${payment.receiptNo}",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = payment.session,
                        fontFamily = ManropeFontFamily,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
                Text(
                    text = payment.date,
                    fontFamily = ManropeFontFamily,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mode badge
                val (modeColor, modeBg) = when (payment.mode.lowercase()) {
                    "online", "upi", "neft", "rtgs" -> TintGreen to SoftGreen
                    "cheque"                         -> Color(0xFF7C3AED) to Color(0xFFEDE9FE)
                    else                             -> TintYellow to SoftYellow // Cash / default
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = modeBg
                ) {
                    Text(
                        text = payment.mode.replaceFirstChar { it.uppercase() },
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = modeColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                // Amount
                Text(
                    text = "₹${"%,.2f".format(payment.amount)}",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = TintGreen
                )
            }
        }
    }
}

@Composable
private fun EmptyFeesState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ReceiptLong,
            contentDescription = null,
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(72.dp)
        )
        Text(
            text = "No Payments Found",
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark
        )
        Text(
            text = "Your fee payment history will appear here",
            fontFamily = ManropeFontFamily,
            fontSize = 14.sp,
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}
