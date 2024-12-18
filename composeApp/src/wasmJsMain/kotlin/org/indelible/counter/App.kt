package org.indelible.counter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.indelible.counter.theme.CustomMaterialTheme
import org.indelible.counter.timers.*

@Composable
fun App() {
    val viewModel = remember { TimerViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isDrawerSheetOpen by remember {
        mutableStateOf(false)
    }

    var shownBackgroundIndex by remember {
        mutableStateOf(0)
    }

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
                        .padding(16.dp)
                ) {
                    OutlinedIconButton(onClick = { isDrawerSheetOpen = true }) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = .6f)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isDrawerSheetOpen,
                    modifier = Modifier.align(Alignment.TopEnd),
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                ){
                    CustomDrawerSheet(
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
        }
    }
}




