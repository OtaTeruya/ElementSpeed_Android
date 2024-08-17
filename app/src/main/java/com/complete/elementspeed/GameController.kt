package com.complete.elementspeed

import kotlin.math.max

class GameController(private val viewModel: GameViewModel,
                     private val isLocalPlay: Boolean) {
    private val playLogs: List<PlayLog> = emptyList()
    data class PlayLog (
        val playerNumber: Int,
        val bahudaIndex: Int,
        val daihudaIndex: Int,
        val timeStamp: String
    )

    fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int, completion: () -> Unit) {
        //logを取る処理は後で考える
        //あと二つ同時に来た時も何とかした方がいいかも。少し待ってもらうみたいな。

        val daihuda = viewModel.getDaihudas().toMutableList()
        val bahudas: MutableList<Element?>
        val tehudas: MutableList<Element?>
        var tehudaNumber: Int
        if (playerNumber == 2) {
            bahudas = viewModel.getBahudas2p().toMutableList()
            tehudas = viewModel.getTehudas2p().toMutableList()
            tehudaNumber = viewModel.getTehudaNumber2p()
        }
        else {//playerNumber==1
            bahudas = viewModel.getBahudas2p().toMutableList()
            tehudas = viewModel.getTehudas2p().toMutableList()
            tehudaNumber = viewModel.getTehudaNumber2p()
        }

        daihuda[daihudaIndex] = bahudas[bahudaIndex] //カードを台札に出す
        tehudaNumber = max(0, tehudaNumber-1) //手札の枚数を一枚減らす

        if (tehudaNumber == 0) {
            bahudas[bahudaIndex] = null
        }
        else {
            bahudas[bahudaIndex] = tehudas[tehudaNumber-1] //出したところに手札からカードを出す
        }

        viewModel.updateDaihudas(daihuda)
        if (playerNumber == 2) {
            viewModel.updateBahudas2p(bahudas)
            viewModel.updateTehudas2p(tehudas)
            viewModel.updateTehudaNumber2p(tehudaNumber)
        }
        else {//playerNumber==1
            viewModel.updateBahudas1p(bahudas)
            viewModel.updateTehudas1p(tehudas)
            viewModel.updateTehudaNumber1p(tehudaNumber)
        }
    }

    fun resetDaihudas() {
        val daihuda = viewModel.getDaihudas().toMutableList()
        val elementProvider = ElementProvider()
        for (i in daihuda.indices) {
            daihuda[i] = elementProvider.getRandomElement()
        }
        viewModel.updateDaihudas(daihuda)
    }
}