package com.complete.elementspeed.util

import com.complete.elementspeed.data.Element
import com.complete.elementspeed.ui.GameViewModel
import kotlin.math.max

class GameController(private val viewModel: GameViewModel) {
    fun playBahuda(playerNumber: Int, bahudaIndex: Int, daihudaIndex: Int, completion: () -> Unit) {
        //logを取る処理は後で考える

        val daihuda = viewModel.daihudas.value.toMutableList()
        val bahudas: MutableList<Element?>
        val tehudas: MutableList<Element?>
        var tehudaNumber: Int
        if (playerNumber == 2) {
            bahudas = viewModel.bahudas2p.value.toMutableList()
            tehudas = viewModel.tehudas2p.value.toMutableList()
            tehudaNumber = viewModel.tehudaNumber2p.value
        }
        else {//playerNumber==1
            bahudas = viewModel.bahudas1p.value.toMutableList()
            tehudas = viewModel.tehudas1p.value.toMutableList()
            tehudaNumber = viewModel.tehudaNumber1p.value
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

        completion()
    }

    fun resetDaihudasWhenNeeded() {
        val daihuda = viewModel.daihudas.value.toMutableList()
        val elementProvider = ElementProvider()
        while (Judge().isToReset(viewModel)) {
            //カードが出せない限りはリセットし続ける
            for (i in daihuda.indices) {
                daihuda[i] = elementProvider.getRandomElement()
            }
            viewModel.updateDaihudas(daihuda)
        }
    }
}