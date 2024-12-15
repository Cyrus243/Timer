package org.indelible.counter.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue


// Comment je fais pour recuperer the value selected ici --> faire remonter les lazyliststate des lazycolumn

@Composable
fun WheelTextPicker(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    count: Int,
    rowCount: Int = 3,
){
    WheelPicker(
        modifier = modifier,
        count = count,
        rowCount = rowCount,
        lazyListState = lazyListState
    ){
        Text(text = it.toString())
    }
}

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    count: Int,
    rowCount: Int,
    size: DpSize = DpSize(24.dp, 120.dp),
    content: @Composable LazyItemScope.(index: Int) -> Unit,
){
    val snapLayoutInfoProvider = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val flingBehavior = rememberSnapFlingBehavior(snapLayoutInfoProvider)


    Box(
        modifier = modifier.background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .width(size.width)
                .background(Color.Transparent),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = size.height / rowCount * ((rowCount - 1 )/ 2)),
            flingBehavior = flingBehavior
        ) {
            items(count) { index ->

                Box(
                    modifier = Modifier
                        .height(size.height / rowCount)
                        .width(size.width)
                        .background(Color.Transparent)
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                snapperLayoutInfo = lazyListState.layoutInfo,
                                index = index,
                                rowCount = rowCount
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    content(index)
                }
            }
        }
    }
}

@Composable
private fun calculateAnimatedAlpha(
    lazyListState: LazyListState,
    snapperLayoutInfo: LazyListLayoutInfo,
    index: Int,
    rowCount: Int
): Float {

    val alpha = animateFloatAsState(
        targetValue = if (lazyListState.firstVisibleItemIndex == index) 1f else .2f,
    )
    return alpha.value
}
