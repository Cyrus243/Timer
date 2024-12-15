package org.indelible.counter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier.height(46.dp),
    value: String,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit = {},
    leadingIcon: @Composable () -> Unit = {},
    placeHolder: String = "",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
){
    var focusState by remember {
        mutableStateOf(false)
    }
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusEvent { state -> focusState = state.hasFocus },
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
        value = value,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Surface {
                Box(modifier = Modifier.fillMaxSize()) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp, vertical = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    leadingIcon()
                                    if (value.isEmpty()){
                                        Text(
                                            text = placeHolder,
                                            style = textStyle.copy(
                                                color = LocalContentColor.current.copy(alpha = .6f)
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                                trailingIcon()
                            }
                            innerTextField()
                        }
                    }
                }
            }
        }
    )
}