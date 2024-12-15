package org.indelible.counter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalTime
import org.indelible.counter.component.CustomTab
import org.indelible.counter.models.TimerOption
import org.indelible.counter.timers.TimerScreenContent
import org.indelible.counter.theme.CustomMaterialTheme
import org.indelible.counter.timers.CountdownConfiguration
import org.indelible.counter.timers.TimerViewModel
import org.indelible.counter.timers.TimerViewModelState

@Composable
fun App() {
    val viewModel = remember { TimerViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isDrawerSheetOpen by remember {
        mutableStateOf(false)
    }

    CustomMaterialTheme(darkTheme = true) {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                TimerScreenContent(
                    hours = uiState.currentTime.hour,
                    minutes = uiState.currentTime.minute,
                    seconds = uiState.currentTime.second,
                    isRunning = !uiState.isPaused,
                    onTimerReset = { viewModel.resetTimer() },
                    onPlayPauseClick = {
                        if (uiState.isPaused) {
                            viewModel.resumeTimer()
                        } else {
                            viewModel.pauseTimer()
                        }
                    }
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
                            contentDescription = null
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
                        startCounting = { viewModel.startCounting() }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomDrawerSheet(
    uiState: TimerViewModelState,
    onClose: () -> Unit,
    onTabClick: (TimerOption) -> Unit,
    selectedTimerOption: TimerOption,
    updateSetTime: (LocalTime) -> Unit,
    updateTitle: (String) -> Unit,
    updateNote: (String) -> Unit,
    startCounting: () -> Unit,
){

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .requiredWidth(400.dp),
        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
        shadowElevation = DrawerDefaults.ModalDrawerElevation
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .requiredWidth(400.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClose
            ){
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }

            CustomTab(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                selectedItemIndex = TimerOption.entries.indexOf(selectedTimerOption),
                items = TimerOption.entries,
                onClick = { option -> onTabClick(option) }
            )
            Spacer(modifier = Modifier.height(24.dp))

            when(selectedTimerOption){
                TimerOption.COUNT_DOWN -> CountdownConfiguration(
                    title = uiState.title,
                    note = uiState.note,
                    setTime = uiState.setTime,
                    onClose = onClose,
                    onSaveChanges = { title, note, setTime ->
                        if (setTime != uiState.setTime){
                            updateSetTime(setTime)
                            startCounting()
                        }

                        updateTitle(title)
                        updateNote(note)
                    }
                )
                else -> {}
            }
        }

    }

}


