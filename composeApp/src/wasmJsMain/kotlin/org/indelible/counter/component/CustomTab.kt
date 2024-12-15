package org.indelible.counter.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.indelible.counter.models.TimerOption

// source: https://medium.com/make-apps-simple/custom-tabs-using-jetpack-compose-e6ff16f961ea


@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<TimerOption>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
    onClick: (option: TimerOption) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        CustomTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            items.mapIndexed { index, timerOption ->
                val isSelected = index == selectedItemIndex
                CustomTabItem(
                    isSelected = isSelected,
                    onClick = {
                        onClick(timerOption)
                    },
                    tabWidth = tabWidth,
                    text = timerOption.label,
                )
            }
        }
    }
}

@Composable
private fun CustomTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) White  else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(easing = LinearEasing),
    )

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .width(tabWidth)
            .padding(vertical = 4.dp),
        text = text,
        color = tabTextColor,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun CustomTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width = indicatorWidth)
            .offset(x = indicatorOffset)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = indicatorColor),
    )
}