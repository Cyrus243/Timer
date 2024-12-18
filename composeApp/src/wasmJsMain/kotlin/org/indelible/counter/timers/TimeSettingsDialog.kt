package org.indelible.counter.timers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import org.indelible.counter.component.CustomTab
import org.indelible.counter.models.TimerOption

@Composable
fun TimerSettingsDialog(
    uiState: TimerViewModelState,
    onClose: () -> Unit,
    onTabClick: (TimerOption) -> Unit,
    selectedTimerOption: TimerOption,
    updateSetTime: (LocalTime) -> Unit,
    updateTitle: (String) -> Unit,
    updateNote: (String) -> Unit,
    startCounting: () -> Unit,
    images: List<String>,
    addImages: (String) -> Unit,
){

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 48.dp)
            .requiredWidth(600.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = DrawerDefaults.ModalDrawerElevation
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

            TimersConfiguration(
                title = uiState.title,
                note = uiState.note,
                setTime = uiState.setTime,
                onClose = onClose,
                images = images,
                onSaveChanges = { title, note, setTime ->
                    when (selectedTimerOption) {
                        TimerOption.COUNT_DOWN -> {
                            if (setTime != uiState.setTime){
                                updateSetTime(setTime)
                                startCounting()
                            }
                        }
                        else -> {
                            updateSetTime(LocalTime(0, 0, 0))
                            startCounting()
                        }
                    }
                    updateTitle(title)
                    updateNote(note)
                },
                selectedOption = selectedTimerOption,
                addImages = addImages
            )
        }

    }

}