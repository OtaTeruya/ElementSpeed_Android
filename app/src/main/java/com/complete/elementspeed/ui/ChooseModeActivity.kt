package com.complete.elementspeed.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.complete.elementspeed.R
import com.complete.elementspeed.util.LevelTextDict

class ChooseModeActivity: ComponentActivity() {
    private var touchPermission = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpFullScreen()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.green)
            ) {
                ChooseModeScreen()
            }
        }
    }

    private fun moveToGameActivityOnClick(level: Int) {
        if (!touchPermission) {
            return
        }
        touchPermission = false

        val iSend = Intent(this, GameActivity::class.java)
        iSend.putExtra("level", level)
        startActivity(iSend)

        Handler(Looper.getMainLooper()).postDelayed({
            touchPermission = true
        }, 2000)
    }

    private val levelTextDict = LevelTextDict()
    @Composable
    fun CustomButtonView(difficulty: Int) {
        Button(
            onClick = {moveToGameActivityOnClick(difficulty)},
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = levelTextDict.getText(difficulty),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }

    @Composable
    fun ChooseModeScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.weight(1f))
            CustomButtonView(0)
            Spacer(modifier = Modifier.height(32.dp))
            CustomButtonView(1)
            Spacer(modifier = Modifier.weight(1f))
        }

        BackHandler(enabled = true) {
            //何もしない
        }
    }

    private fun setUpFullScreen() {
        // 下のタブを消してフルスクリーンにする
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.apply {
                hide(WindowInsets.Type.systemBars())
                systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            // API 29以下の場合
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}