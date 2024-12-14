package org.indelible.counter.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun TimerScreenContent(){

    var shouldShowControl by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        //color = Color(0xFF0D1B2A)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                KamelImage(
                    resource = { asyncPainterResource("https://cdn.pixabay.com/photo/2023/01/08/05/52/sunset-7704594_1280.jpg") },
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "00:00",
                        fontSize = 132.sp
                    )
                }

                OutlinedIconButton(
                    onClick = { shouldShowControl = !shouldShowControl }
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.TimerOff,
                        contentDescription = null
                    )
                }

                AnimatedVisibility(visible = shouldShowControl) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Stop")
                        }

                        FilledTonalButton(onClick = {}) {
                            Text(text = if (true) "Pause" else "Start")
                        }
                    }
                }
            }
        }
    }
}