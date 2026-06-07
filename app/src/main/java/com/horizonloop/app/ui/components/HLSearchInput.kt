package com.horizonloop.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horizonloop.app.ui.theme.Dark
import com.horizonloop.app.ui.theme.Mid
import com.horizonloop.app.ui.theme.Muted
import com.horizonloop.app.ui.theme.Muted50
import com.horizonloop.app.ui.theme.White12
import com.horizonloop.app.ui.theme.White15

@Composable
fun HLSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search lessons...",
    modifier: Modifier = Modifier,
) {
    var focused by remember { mutableStateOf(false) }
    val bg = if (focused) White15 else Muted50

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .border(1.dp, if (focused) Mid else Muted, RoundedCornerShape(999.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = Mid,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(start = 28.dp)
                .onFocusChanged { focused = it.isFocused },
            textStyle = LocalTextStyle.current.merge(
                TextStyle(
                    color = Dark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            ),
            singleLine = true,
            cursorBrush = SolidColor(Dark),
            keyboardOptions = KeyboardOptions.Default,
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
