package org.indelible.counter.timers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.LocalTime
import org.indelible.counter.*
import org.indelible.counter.component.CustomTextField
import org.indelible.counter.component.WheelTextPicker
import org.indelible.counter.models.TimerOption

@Composable
fun CountdownConfiguration(
    title: String,
    note: String,
    setTime: LocalTime,
    onSaveChanges: (title: String, note: String, setTime: LocalTime) -> Unit,
    onClose: () -> Unit,
    addImages: (String) -> Unit,
    selectedOption: TimerOption,
    images: List<String>,
){
    var updatedTitle by remember {
        mutableStateOf(title)
    }
    var updatedNote by remember {
        mutableStateOf(note)
    }
    var updatedTime by remember {
        mutableStateOf(setTime)
    }

    var imageUrl by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .pointerInput(Unit){
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ){
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Timer title",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    value = updatedTitle,
                    onValueChange = { updatedTitle = it },
                    placeHolder = "Timer title here!",
                    textStyle = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp),
                    value = updatedNote,
                    onValueChange = { updatedNote = it },
                    placeHolder = "Maybe a description here!",
                    textStyle = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(selectedOption == TimerOption.COUNT_DOWN){
                    Column {
                        Text(
                            text = "Duration",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DurationSelector(
                            updatedTime = updatedTime,
                            onTimeUpdate = { hour, min, second ->
                                updatedTime = LocalTime(hour, min, second)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }


                Text(
                    text = "Background",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(46.dp),
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        placeHolder = "Backgrounds url here!",
                        textStyle = MaterialTheme.typography.bodySmall,
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    FilledTonalButton(
                        onClick = {
                            addImages(imageUrl)
                            imageUrl = ""
                        },
                        shape = RoundedCornerShape(8.dp)
                    ){
                        Text(
                            text = "Add",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                ImageRow(images)
            }

            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onClose() },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                FilledTonalButton(
                    onClick = {
                        onSaveChanges(updatedTitle, updatedNote, updatedTime)
                        onClose()
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun ImageRow(imageUrls: List<String>){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(imageUrls){ image ->
            ImageItem(imageUrl = image)
        }
    }
}

@Composable
fun ImageItem(imageUrl: String){
    BadgedBox(
        badge = {
            Surface(
                modifier = Modifier.align(Alignment.TopEnd),
                tonalElevation = 4.dp,
                shape = CircleShape
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(4.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    ){
        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(8.dp)
        ){
            KamelImage(
                resource = { asyncPainterResource(imageUrl) },
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun DurationSelector(
    updatedTime: LocalTime,
    onTimeUpdate: (hour: Int, min: Int, second: Int) -> Unit,
){

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .requiredHeight(46.dp),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder()
        ) {}

        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DurationItemCreator(
                value = updatedTime.hour.toString(),
                unit = "hours",
                count = HOURS_COUNT,
            ){ onTimeUpdate(it, updatedTime.minute, updatedTime.second) }
            VerticalDivider(modifier = Modifier.requiredHeight(46.dp))

            DurationItemCreator(
                value = updatedTime.minute.toString(),
                unit = "minutes",
                count = MINUTES_COUNT,
            ){ onTimeUpdate(updatedTime.hour, it, updatedTime.second) }
            VerticalDivider(modifier = Modifier.requiredHeight(46.dp))

            DurationItemCreator(
                value = updatedTime.second.toString(),
                unit = "seconds",
                count = SECONDS_COUNT,
            ){ onTimeUpdate(updatedTime.hour, updatedTime.minute, it) }
        }
    }

}

@Composable
fun DurationItemCreator(
    modifier: Modifier = Modifier,
    value: String,
    unit: String,
    count: Int,
    onValueChange: (Int) -> Unit,
){
    var isSelected by remember {
        mutableStateOf(false)
    }
    val state = rememberLazyListState()
    DurationItem(
        modifier = modifier
            .onFocusEvent { focusState ->
                if (!focusState.isFocused && isSelected) {
                    onValueChange(state.firstVisibleItemIndex)
                }
                isSelected = focusState.isFocused
            }
            .clickable(
                onClick = { isSelected = true },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        value = value,
        unit = unit,
        count = count,
        lazyListState = state,
        isSelected = isSelected
    )
}

@Composable
fun DurationItem(
    modifier: Modifier = Modifier,
    value: String,
    unit: String,
    count: Int,
    lazyListState: LazyListState,
    isSelected: Boolean,
){
    Surface(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedContent(isSelected){ state ->
                if (state) {
                    WheelTextPicker(
                        count = count,
                        lazyListState = lazyListState
                    )
                }else{
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text(
                text = unit,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LocalContentColor.current.copy(alpha = .6f)
                )
            )

            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )

        }
    }

}