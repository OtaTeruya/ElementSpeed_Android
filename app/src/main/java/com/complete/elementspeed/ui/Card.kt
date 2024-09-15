package com.complete.elementspeed.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.complete.elementspeed.R
import com.complete.elementspeed.data.Element

@Composable
fun Card(element: Element?, hintIsNeeded: Boolean = true) {
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    (LocalConfiguration.current.screenWidthDp * 0.22).dp,
                    (LocalConfiguration.current.screenWidthDp * 0.36).dp
                )
                .border(
                    width = 1.dp,
                    color = (if (it != null) Color.Black else Color.Transparent),
                    shape = RoundedCornerShape(10)
                )
                .background(
                    color = (if (it != null) Color.White else Color.Transparent),
                    shape = RoundedCornerShape(10)
                )
                .padding(4.dp)
        ) {
            if (it != null) {
                Image(
                    painter = painterResource(id = R.drawable.element_image),
                    contentDescription = null
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    if (hintIsNeeded) {
                        Row {
                            Text(
                                text = it.number.toString(),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    AutoSizableText(
                        text = it.symbol,
                        minFontSize = 12.sp,
                        maxFontSize = 48.sp
                    )

                    if (hintIsNeeded) {
                        AutoSizableText(
                            text = it.nameJa,
                            minFontSize = 8.sp,
                            maxFontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (hintIsNeeded) {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = when (it.group) {
                                    101 -> "la"
                                    102 -> "ac"
                                    else -> it.group.toString()
                                },
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}