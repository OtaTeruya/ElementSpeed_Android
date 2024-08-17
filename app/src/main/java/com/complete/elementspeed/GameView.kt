package com.complete.elementspeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class GameView: AppCompatActivity(), MyCallback {
    private val tehudaGenerator = TehudaGenerator()
    private lateinit var computerPlayer: ComputerPlayer
    private lateinit var gameController: GameController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_game)

        initializeAllCard()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly2p, Player2Fragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly1p, Player1Fragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_daihuda, DaihudaFragment())
            .commit()

        computerPlayer = ComputerPlayer(
            ViewModelProvider(this)[GameViewModel::class.java], this
        )
        gameController = GameController(
            ViewModelProvider(this)[GameViewModel::class.java], true
        )
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
        val viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        val tehuda2p = tehudaGenerator.generateTehudas()
        viewModel.updateTehudas2p(tehuda2p)
        viewModel.updateBahudas2p(tehuda2p.takeLast(BAHUDA_NUM))
        viewModel.updateTehudaNumber2p(tehuda2p.size - BAHUDA_NUM)

        val tehuda1p = tehudaGenerator.generateTehudas()
        viewModel.updateTehudas1p(tehuda1p)
        viewModel.updateBahudas1p(tehuda1p.takeLast(BAHUDA_NUM))
        viewModel.updateTehudaNumber1p(tehuda1p.size - BAHUDA_NUM)

        val DAIHUDA_NUM = 2
        val daihudaList: MutableList<Element> = mutableListOf()
        for (i in 0 until DAIHUDA_NUM) {
            daihudaList += tehudaGenerator.getRandomElement()
        }
        viewModel.updateDaihudas(daihudaList)
    }

    override fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int) {
        gameController.playBahuda(playerNumber, bahudaIndex, daihudaIndex)
    }
}

interface MyCallback {
    fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int)
}