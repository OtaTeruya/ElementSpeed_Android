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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.complete.elementspeed.R
import com.complete.elementspeed.util.LevelData

class ChooseModeActivity: ComponentActivity() {
    private var touchPermission = true
    private var level by mutableIntStateOf(0)
    private val levelData = LevelData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.green)
            ) {
                ChooseModeScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setUpFullScreen()
    }

    private fun changeLevel(direction: Int) {
        if (!touchPermission) {
            return
        }
        touchPermission = false
        level = (level + direction + levelData.LEVEL_SIZE) % levelData.LEVEL_SIZE
        touchPermission = true
    }

    private fun moveToGameActivityOnClick() {
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

    @Composable
    fun ChooseModeScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {changeLevel(-1)},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(32.dp, 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.left_triangle),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp, 32.dp)
                    )
                }

                Text(
                    text = levelData.getText(level),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.width(180.dp)
                )

                Button(
                    onClick = {changeLevel(1)},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(32.dp, 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.right_triangle),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp, 32.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(300.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "1 ~ " + levelData.getElementNumMax(level),
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )

                if (levelData.getJudgeRule(level).isNumberUsed) {
                    Text(
                        text = "原子番号が１つ違い",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (levelData.getJudgeRule(level).isGroupUsed) {
                    Text(
                        text = "同族の元素",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (levelData.getJudgeRule(level).isTextLengthUsed) {
                    Text(
                        text = "元素名の文字数が１つ違い",
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Text(
                    text = if (levelData.getHintIsNeeded(level)) "ヒントあり" else "ヒントなし",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Button(
                onClick = {moveToGameActivityOnClick()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Play!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green)
                )
            }

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