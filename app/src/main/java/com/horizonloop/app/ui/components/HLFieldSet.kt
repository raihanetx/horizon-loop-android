package com.horizonloop.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Muted

@Composable
fun HLFieldSet(
    label: String,
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    minHeight: androidx.compose.ui.unit.Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
) {
    var focused by remember { mutableStateOf(false) }
    val borderColor = if (focused) Mid else Muted
    val borderWidth = if (focused) 2.dp else 1.dp
    val padding = if (focused) 5.dp else 6.dp
    val horizontalPad = if (focused) 11.dp else 12.dp

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .border(borderWidth, borderColor, RoundedCornerShape(6.dp))
                .padding(horizontal = horizontalPad, vertical = padding)
        ) {
            Text(
                text = label.uppercase(),
                color = Mid,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.1.sp,
                modifier = Modifier
                    .offset(x = (-4).dp)
                    .background(Mid.copy(alpha = 0f))
                    .padding(horizontal = 4.dp)
            )
            if (focused) {
                Text(
                    text = label.uppercase(),
                    color = Mid,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.1.sp,
                    modifier = Modifier.offset(x = (-4).dp, y = (-14).dp)
                )
            }

            val textStyle = LocalTextStyle.current.merge(
                TextStyle(
                    color = Dark,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focused = it.isFocused }
                    .heightIn(min = minHeight)
                    .padding(top = 4.dp),
                textStyle = textStyle,
                singleLine = singleLine,
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = VisualTransformation.None,
                cursorBrush = SolidColor(Dark),
                decorationBox = { inner ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Mid,
                            fontSize = 14.sp,
                        )
                    }
                    inner()
                }
            )
        }
    }
}
