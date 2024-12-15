package org.indelible.counter.timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import org.indelible.counter.models.TimerOption
import org.indelible.counter.plusSecond

class TimerViewModel: ViewModel() {

    val _uiState = MutableStateFlow(TimerViewModelState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    private fun updateCurrentTime(time: LocalTime){
        _uiState.update {
            it.copy(currentTime = time)
        }
    }

    fun updateSetTime(time: LocalTime) {
        _uiState.update {
            it.copy(setTime = time)
        }
    }

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun updateNote(note: String) {
        _uiState.update {
            it.copy(note = note)
        }
    }

    fun updateTimerOption(timerOption: TimerOption) {
        _uiState.update {
            it.copy(timerOption = timerOption)
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update {
            it.copy(isPaused = true)
        }
    }

    fun resumeTimer() {
        _uiState.update { it.copy(isPaused = false) }
        launchJob()
    }

    fun resetTimer() {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            isPaused = true,
            currentTime = uiState.value.setTime
        )
    }

    fun startCounting(){
        timerJob?.cancel()
        resetTimer()

        _uiState.update { it.copy(isPaused = false) }
        launchJob()
    }

    private fun launchJob(){
        val increment = if (_uiState.value.timerOption == TimerOption.COUNT_DOWN) -1 else 1

        timerJob = viewModelScope.launch {
            while (!uiState.value.isPaused){
                updateCurrentTime(uiState.value.currentTime.plusSecond(increment))
                delay(1000)
            }
        }
    }
}

data class TimerViewModelState(
    val setTime: LocalTime = LocalTime(0, 0, 0),
    val currentTime: LocalTime = LocalTime(0, 0, 0),
    val title: String = "",
    val note: String = "",
    val isPaused: Boolean = true,
    val timerOption: TimerOption = TimerOption.COUNT_DOWN,
)