package com.complete.elementspeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Card(element: Element) {
    Column(
        modifier = Modifier
            .size(
                (LocalConfiguration.current.screenWidthDp * 0.2).dp,
                (LocalConfiguration.current.screenHeightDp * 0.2).dp
            )
            .background(Color.Gray)
    ){
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = element.elementNameJa,
            fontSize = 28.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}