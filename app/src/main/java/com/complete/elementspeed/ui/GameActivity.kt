package com.complete.elementspeed.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import com.complete.elementspeed.util.ComputerPlayer
import com.complete.elementspeed.data.Element
import com.complete.elementspeed.util.ElementProvider
import com.complete.elementspeed.util.GameController
import com.complete.elementspeed.util.Judge
import com.complete.elementspeed.R
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GameActivity: AppCompatActivity(), MyCallback {
    private lateinit var viewModel: GameViewModel
    private var level = 0
    private lateinit var elementProvider: ElementProvider
    private lateinit var computerPlayer: ComputerPlayer
    private lateinit var gameController: GameController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_game)
        setUpFullScreen()
        level = intent.getIntExtra("level", 0)
        elementProvider = ElementProvider(level)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        initializeAllCard()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_2p, Player2Fragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_1p, Player1Fragment(this))
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_daihuda, DaihudaFragment())
            .commit()

        computerPlayer = ComputerPlayer(
            ViewModelProvider(this)[GameViewModel::class.java], this
        )
        gameController = GameController(
            ViewModelProvider(this)[GameViewModel::class.java], level
        )

        //カードが全く出せない場合はリセットを行う
        gameController.resetDaihudasWhenNeeded()
    }

    override fun onResume() {
        super.onResume()
        computerPlayer.startRepeatingTask()
    }

    override fun onPause() {
        super.onPause()
        computerPlayer.stopRepeatingTask()
    }

    private fun initializeAllCard() {
        val BAHUDA_NUM = 4

        val tehuda2p = elementProvider.generateTehudas()
        viewModel.updateTehudas2p(tehuda2p)
        viewModel.updateBahudas2p(tehuda2p.takeLast(BAHUDA_NUM))
        viewModel.updateTehudaNumber2p(tehuda2p.size - BAHUDA_NUM)

        val tehuda1p = elementProvider.generateTehudas()
        viewModel.updateTehudas1p(tehuda1p)
        viewModel.updateBahudas1p(tehuda1p.takeLast(BAHUDA_NUM))
        viewModel.updateTehudaNumber1p(tehuda1p.size - BAHUDA_NUM)

        val DAIHUDA_NUM = 2
        val daihudaList: MutableList<Element> = mutableListOf()
        for (i in 0 until DAIHUDA_NUM) {
            daihudaList += elementProvider.getRandomElement()
        }
        viewModel.updateDaihudas(daihudaList)
    }

    private val mutex = Mutex()
    override suspend fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int) {
        mutex.withLock {
            gameController.playBahuda(playerNumber, bahudaIndex, daihudaIndex) {
                val winner = Judge().gameWinner(viewModel)
                if (winner != null) {
                    //勝者が決まった場合
                    computerPlayer.stopRepeatingTask()

                    val popupView = findViewById<ComposeView>(R.id.popup_view)
                    popupView.setContent {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Transparent
                        ) {
                            PopupToShowResult(winner) { finishGame() }
                        }
                    }
                    popupView.visibility = View.VISIBLE
                }
                else {
                    gameController.resetDaihudasWhenNeeded()
                }
            }
        }
    }

    override suspend fun failToPlayBahuda() {
        mutex.withLock {
            gameController.failToPlayBahuda()

            val frameLayout = findViewById<FrameLayout>(R.id.frame_layout)
            val overlayView = View(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(getColor(R.color.transparentRed))
            }
            frameLayout.addView(overlayView)

            Handler(Looper.getMainLooper()).postDelayed({
                frameLayout.removeView(overlayView)
            }, 500)
        }
    }

    private var touchPermission = true //二度実行されることの防止
    private fun finishGame() {
        if (!touchPermission) {
            return
        }
        touchPermission = false
        finish()
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

interface MyCallback {
    suspend fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int)
    suspend fun failToPlayBahuda()
}