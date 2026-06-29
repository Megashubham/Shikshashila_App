package com.shikshashila.app.ui.student.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shikshashila.app.data.model.Note
import com.shikshashila.app.ui.student.FeatureState
import com.shikshashila.app.ui.student.StudentFeatureViewModel
import com.shikshashila.app.ui.theme.ManropeFontFamily

private val BlueStart  = Color(0xFF2563EB)
private val BgColor    = Color(0xFFF8F9FA)
private val TextDark   = Color(0xFF111827)
private val TextMuted  = Color(0xFF6B7280)
private val TintRed    = Color(0xFFDC2626)
private val PurpleBg   = Color(0xFFF3E8FF)
private val PurpleTint = Color(0xFF7C3AED)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentNotesScreen(
    viewModel: StudentFeatureViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val notesState by viewModel.notesState.collectAsState()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        if (notesState is FeatureState.Idle) {
            viewModel.fetchNotes()
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Study Notes",
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
            when (val state = notesState) {
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
                            onClick = { viewModel.fetchNotes() },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueStart)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily)
                        }
                    }
                }
                is FeatureState.Success -> {
                    val notes = state.data.notes

                    if (notes.isEmpty()) {
                        EmptyNotesState(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Text(
                                    text = "${notes.size} Note${if (notes.size != 1) "s" else ""}",
                                    fontFamily = ManropeFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = TextDark
                                )
                            }
                            items(notes, key = { it.id }) { note ->
                                NoteCard(
                                    note = note,
                                    onDownload = { url ->
                                        try { uriHandler.openUri(url) } catch (_: Exception) {}
                                    }
                                )
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
private fun NoteCard(
    note: Note,
    onDownload: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Subject chip + optional download
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = PurpleBg
                ) {
                    Text(
                        text = note.subject,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = PurpleTint,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                if (!note.fileUrl.isNullOrBlank()) {
                    IconButton(
                        onClick = { onDownload(note.fileUrl) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFDBEAFE), RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = BlueStart,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Title
            Text(
                text = note.title,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextDark
            )

            Spacer(Modifier.height(4.dp))

            // Description (2 lines max)
            Text(
                text = note.description,
                fontFamily = ManropeFontFamily,
                fontSize = 13.sp,
                color = TextMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(Modifier.height(10.dp))

            // Date at bottom right
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = note.createdAt,
                    fontFamily = ManropeFontFamily,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
private fun EmptyNotesState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.MenuBook,
            contentDescription = null,
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(72.dp)
        )
        Text(
            text = "No Notes Yet",
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark
        )
        Text(
            text = "Study notes shared by your teachers will appear here",
            fontFamily = ManropeFontFamily,
            fontSize = 14.sp,
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}
