package org.indelible.counter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.indelible.counter.theme.CustomMaterialTheme
import org.indelible.counter.timers.*

@Composable
fun App() {
    val viewModel = remember { TimerViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isDrawerSheetOpen by remember { mutableStateOf(false) }
    var shownBackgroundIndex by remember { mutableStateOf(0) }
    var isAppearanceDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(!uiState.isPaused){
        while (true){
            delay(10000L)
            shownBackgroundIndex = ++shownBackgroundIndex % uiState.selectedBackground.size
        }
    }

    CustomMaterialTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {

                TimerScreenContent(
                    hours = uiState.currentTime.hour,
                    minutes = uiState.currentTime.minute,
                    seconds = uiState.currentTime.second,
                    isRunning = !uiState.isPaused,
                    title = uiState.title,
                    note = uiState.note,
                    onTimerReset = { viewModel.resetTimer() },
                    onPlayPauseClick = {
                        if (uiState.isPaused) {
                            viewModel.resumeTimer()
                        } else {
                            viewModel.pauseTimer()
                        }
                    },
                    shownBackgroundIndex = shownBackgroundIndex,
                    backgroundList = uiState.selectedBackground,
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    TextButton(
                        onClick = { isDrawerSheetOpen = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(
                            text = "TIMER SETTINGS",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    TextButton(
                        onClick = { isAppearanceDialogOpen = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text(
                            text = "APPEARANCE",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                AnimatedVisibility(visible = isDrawerSheetOpen){
                    Dialog(onDismissRequest = { isDrawerSheetOpen = false }){
                        TimerSettingsDialog(
                            onClose = { isDrawerSheetOpen = false },
                            onTabClick = { viewModel.updateTimerOption(it) },
                            selectedTimerOption = uiState.timerOption,
                            uiState = uiState,
                            updateNote = { viewModel.updateNote(it) },
                            updateTitle = { viewModel.updateTitle(it) },
                            updateSetTime = { viewModel.updateSetTime(it) },
                            startCounting = { viewModel.startCounting() },
                            images = uiState.selectedBackground,
                            addImages = { viewModel.addImageBackground(it) }
                        )
                    }
                }

                AnimatedVisibility(visible = isAppearanceDialogOpen){
                    Dialog(onDismissRequest = { isAppearanceDialogOpen = false }){
                        Card(
                            modifier = Modifier
                                .requiredHeight(200.dp)
                                .requiredWidth(300.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "Work in progress !"
                                )

                                FilledTonalButton(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    onClick = { isAppearanceDialogOpen = false },
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Text(
                                        text = "Close",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




