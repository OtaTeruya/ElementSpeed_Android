package com.complete.elementspeed

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Card(element: Element?) {
    // 前のelementを保持
    var currentElement by remember { mutableStateOf(element) }
    var isAnimating by remember { mutableStateOf(false) }

    // elementが変更されたらアニメーションを開始
    LaunchedEffect(element) {
        if (element != currentElement) {
            isAnimating = true
        }
    }

    // アニメーションが終了したら新しいelementを適用
    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            isAnimating = false
            currentElement = element
        }
    }

    AnimatedContent(
        targetState = element,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) {it ->
        Column(
            modifier = Modifier
                .size(
                    (LocalConfiguration.current.screenWidthDp * 0.2).dp,
                    (LocalConfiguration.current.screenHeightDp * 0.2).dp
                )
                .background(if(it!=null) Color.Gray else Color.Transparent)
        ) {
            if (it != null) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = it.elementNameJa,
                    fontSize = 28.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}