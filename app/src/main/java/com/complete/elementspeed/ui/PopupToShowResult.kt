package com.complete.elementspeed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PopupToShowResult(levelText: String, winner: String, action: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8F))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .padding(horizontal = 8.dp)
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RectangleShape
                )
                .padding(12.dp)
        ) {
            Text(
                text = levelText,
                fontSize = 24.sp,
            )

            Text(
                text = winner + "の勝ち！",
                fontSize = 24.sp,
                modifier = Modifier.padding(12.dp)
            )

            Button(
                onClick = {
                    action()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(49, 63, 25)
                )
            ) {
                Text(
                    text = "OK",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}