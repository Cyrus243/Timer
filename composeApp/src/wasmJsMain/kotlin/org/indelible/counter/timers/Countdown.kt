package org.indelible.counter.timers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.indelible.counter.formattedZeroTimes

@Composable
fun TimerScreenContent(
    hours: Int,
    minutes: Int,
    seconds: Int,
    title: String,
    note: String,
    isRunning: Boolean,
    shownBackgroundIndex: Int,
    backgroundList: List<String>,
    onTimerReset: () -> Unit,
    onPlayPauseClick: () -> Unit,
){

    var shouldShowControl by remember {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                KamelImage(
                    resource = { asyncPainterResource(backgroundList[shownBackgroundIndex]) },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    animationSpec = tween(durationMillis = 500)
                )
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = .4f),
            ) {}


            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 48.sp, color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (note.isNotEmpty()) {
                    Text(
                        text = note,
                        style = MaterialTheme.typography.labelMedium.copy(color = Color.White.copy(alpha = .6f))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row {
                    Text(
                        text = "${hours.formattedZeroTimes()}:${minutes.formattedZeroTimes()}:${seconds.formattedZeroTimes()}",
                        fontSize = 132.sp,
                        color = Color.White,
                    )
                }

                OutlinedIconButton(
                    onClick = { shouldShowControl = !shouldShowControl },
                    colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = Color.White),
                    border = IconButtonDefaults.outlinedIconButtonBorder(
                        enabled = true
                    ).copy(brush = SolidColor(Color.White))
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = if (shouldShowControl) Icons.Outlined.TimerOff else Icons.Outlined.Timer,
                        contentDescription = null
                    )
                }

                AnimatedVisibility(visible = shouldShowControl) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(onClick = { onTimerReset() }) {
                            Text(text = "Stop")
                        }

                        FilledTonalButton(onClick = { onPlayPauseClick() }) {
                            Text(text = if (isRunning) "Pause" else "Start")
                        }
                    }
                }
            }
        }
    }
}