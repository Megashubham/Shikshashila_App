package com.shikshashila.app.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.User
import com.shikshashila.app.ui.theme.*

// ─── Website colors (local shorthand) ────────────────────────────────────
private val Purple  = Color(0xFF8B5CF6)
private val Pink    = Color(0xFFEC4899)
private val Dark    = Color(0xFF374151)
private val Muted   = Color(0xFF6B7280)
private val BgFrom  = Color(0xFFDFE9F3)
private val BgTo    = Color(0xFFFFFFFF)
private val Border  = Color(0xFFE5E7EB)
private val Label   = Color(0xFF374151)
private val PlaceholderColor = Color(0xFFADB5BD)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (User) -> Unit
) {
    var loginId  by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess((authState as AuthState.Success).user)
            viewModel.resetState()
        }
    }

    // ── Full-screen gradient background (#dfe9f3 → white, same as website mask-3) ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors    = listOf(BgFrom, BgTo),
                    start     = Offset(0f, Float.POSITIVE_INFINITY),
                    end       = Offset(0f, 0f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── LEFT SIDE hero text (shown at top on mobile, matches col-lg-7 on desktop) ──
            AnimatedVisibility(
                visible = true,
                enter   = fadeIn() + slideInVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text       = buildAnnotatedString {
                            append("Shikshashila: The\nNext-Generation\n")
                            withStyle(SpanStyle(
                                brush = Brush.linearGradient(listOf(Purple, Pink))
                            )) { append("School ERP") }
                        },
                        fontSize   = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = Color(0xFF0F172A),
                        lineHeight = 38.sp,
                        fontFamily = ManropeFontFamily
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text       = "Experience the future of school management with Shikshashila.",
                        fontSize   = 14.sp,
                        color      = Muted,
                        lineHeight = 22.sp,
                        fontFamily = ManropeFontFamily
                    )
                }
            }

            // ── WHITE CARD (matches card-auth rounded-4 on website) ──
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape     = RoundedCornerShape(16.dp),
                        ambientColor = Color(0x1A000000),
                        spotColor    = Color(0x1A000000)
                    ),
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    // ── Card header: title + Shikshashila logo (matches nk-block-head-content) ──
                    Row(
                        modifier            = Modifier.fillMaxWidth(),
                        verticalAlignment   = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text       = "Login Page",
                                fontSize   = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color      = Color(0xFF0F172A),
                                fontFamily = ManropeFontFamily
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text       = "Please sign-in to your account !",
                                fontSize   = 13.sp,
                                color      = Muted,
                                fontFamily = ManropeFontFamily
                            )
                        }

                        // Graduation cap + "Shikshashila" branding (top-right of card)
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "🎓", fontSize = 28.sp)
                            Text(
                                text       = "Shikshashila",
                                fontSize   = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle  = FontStyle.Normal,
                                color      = Purple,
                                fontFamily = ManropeFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ── Login ID field ──
                    Text(
                        text       = "Login ID",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Label,
                        fontFamily = ManropeFontFamily,
                        modifier   = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value         = loginId,
                        onValueChange = { loginId = it },
                        placeholder   = { Text("Enter Login ID", color = PlaceholderColor, fontSize = 14.sp) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape         = RoundedCornerShape(8.dp),
                        leadingIcon   = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Purple,
                            unfocusedBorderColor = Border,
                            focusedLabelColor    = Purple,
                            cursorColor          = Purple,
                            focusedContainerColor   = Color.White,
                            unfocusedContainerColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // ── Password field ──
                    Text(
                        text       = "Password",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Label,
                        fontFamily = ManropeFontFamily,
                        modifier   = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value         = password,
                        onValueChange = { password = it },
                        placeholder   = { Text("Enter Password", color = PlaceholderColor, fontSize = 14.sp) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true,
                        visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape         = RoundedCornerShape(8.dp),
                        leadingIcon   = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon  = {
                            IconButton(onClick = { showPass = !showPass }) {
                                Icon(
                                    imageVector = if (showPass) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (showPass) "Hide password" else "Show password",
                                    tint = Muted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Purple,
                            unfocusedBorderColor = Border,
                            focusedLabelColor    = Purple,
                            cursorColor          = Purple,
                            focusedContainerColor   = Color.White,
                            unfocusedContainerColor = Color.White,
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // ── Error message ──
                    if (authState is AuthState.Error) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp),
                            shape  = RoundedCornerShape(8.dp),
                            color  = Color(0xFFFEE2E2)
                        ) {
                            Text(
                                text     = (authState as AuthState.Error).message,
                                color    = Color(0xFFDC2626),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                fontFamily = ManropeFontFamily
                            )
                        }
                    }

                    // ── Login button (btn-secondary dark gray, same as website) ──
                    Button(
                        onClick  = { viewModel.login(loginId, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled  = authState !is AuthState.Loading,
                        shape    = RoundedCornerShape(8.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor         = Dark,        // #374151 — website btn-secondary
                            contentColor           = Color.White,
                            disabledContainerColor = Color(0xFF9CA3AF),
                            disabledContentColor   = Color.White
                        )
                    ) {
                        if (authState is AuthState.Loading) {
                            CircularProgressIndicator(
                                modifier    = Modifier.size(22.dp),
                                color       = Color.White,
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Text(
                                text       = "Login",
                                fontSize   = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = ManropeFontFamily
                            )
                        }
                    }
                } // Card content end
            } // Card end

            Spacer(modifier = Modifier.height(24.dp))

            // ── Bottom branding ──
            Text(
                text       = "© Shikshashila School ERP",
                fontSize   = 12.sp,
                color      = Color(0xFFADB5BD),
                fontFamily = ManropeFontFamily
            )
        }
    }
}
