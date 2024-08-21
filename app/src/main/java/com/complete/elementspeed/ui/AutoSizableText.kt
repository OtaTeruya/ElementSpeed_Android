package com.complete.elementspeed.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun AutoSizableText(
    text: String,
    minFontSize: TextUnit,
    maxFontSize: TextUnit,
    modifier: Modifier = Modifier,
    maxLineNum: Int = 1,
    granularityInPx: Int = 1,
) {
    val autoSizer = rememberAutoSizer(minFontSize, maxFontSize, granularityInPx)
    val style = LocalTextStyle.current.copy(color = Color.Black)

    BoxWithConstraints(modifier = modifier) {
        val fontSize = remember(text, autoSizer, style, constraints) {
            autoSizer.autoSize(
                text = text,
                style = style,
                maxLineNum = maxLineNum,
                constraints = constraints,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = text,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            style = style
        )
    }
}