package org.indelible.counter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.indelible.counter.component.CustomTab
import org.indelible.counter.component.CustomTextField
import org.indelible.counter.component.WheelPicker
import org.indelible.counter.component.WheelTextPicker
import org.indelible.counter.timer.TimerScreenContent
import org.indelible.counter.theme.CustomMaterialTheme

@Composable
fun App() {
    var isDrawerSheetOpen by remember {
        mutableStateOf(false)
    }

    CustomMaterialTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier.fillMaxSize()) {
                TimerScreenContent()
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
                    CustomDrawerSheet(onClose = { isDrawerSheetOpen = false })
                }
            }
        }
    }
}

@Composable
fun CustomDrawerSheet(onClose: () -> Unit){

    var (selectedTabIndex, setSelectedIndex) = remember {
        mutableStateOf(0)
    }

    val tabList = remember {
        mutableStateListOf("Countdown", "Chronometer", "Concentration mode")
    }
    Box(
        modifier = Modifier.fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .requiredWidth(400.dp),
            shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
            shadowElevation = DrawerDefaults.ModalDrawerElevation
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .requiredWidth(400.dp)
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onClose
                    ){
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }

                    CustomTab(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        selectedItemIndex = selectedTabIndex,
                        items = tabList,
                        onClick = { index -> setSelectedIndex(index) }
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Timer title",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        placeHolder = "Timer name here!",
                        textStyle = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        placeHolder = "Maybe a description here!",
                        textStyle = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Duration",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DurationSelector()
                }

                Row(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(onClick = {}) {
                        Text(text = "Cancel")
                    }

                    FilledTonalButton(onClick = {}) {
                        Text(text = "Save")
                    }
                }
            }

        }
    }
}

@Composable
fun DurationSelector(){

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
            DurationItem(
                value = "0",
                unit = "hours",
                count = HOURS_COUNT
            )
            VerticalDivider(modifier = Modifier.requiredHeight(46.dp))

            DurationItem(
                value = "0",
                unit = "minutes",
                count = MINUTES_COUNT
            )
            VerticalDivider(modifier = Modifier.requiredHeight(46.dp))

            DurationItem(
                value = "0",
                unit = "seconds",
                count = SECONDS_COUNT
            )
        }
    }

}

@Composable
fun DurationItem(
    value: String,
    unit: String,
    count: Int,
){
    var isSelected by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable(
                onClick = { isSelected = !isSelected },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedContent(isSelected){ state ->
                if (state) {
                    WheelTextPicker(count = count)
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

