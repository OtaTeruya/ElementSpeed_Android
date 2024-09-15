package com.complete.elementspeed.util

import com.complete.elementspeed.data.Element
import com.complete.elementspeed.ui.GameViewModel
import kotlin.math.abs

class Judge(private val judgeRule: JudgeRule) {
    fun isBahudaPlaybale(bahuda: Element, daihuda: Element): Boolean {
        //同じ原子の場合は出せない
        if (bahuda.number == daihuda.number) {
            return false
        }

        //原子番号が一つ違いの場合
        if (judgeRule.isNumberUsed) {
            if (abs(bahuda.number - daihuda.number) == 1) {
                return true
            }
        }

        //同族の場合
        if (judgeRule.isGroupUsed) {
            if (bahuda.group==daihuda.group) {
                return true
            }
        }

        //元素名の文字数が１つ違い
        if (judgeRule.isTextLengthUsed) {
            if (abs(bahuda.textLength - daihuda.textLength) == 1) {
                return true
            }
        }

        return false
    }

    fun searchPlayableCard(bahudas: List<Element?>, daihudas: List<Element?>)
    : Triple<Boolean, Int, Int> {
        var isPlayable = false
        var bahudaIndex = 0
        var daihudaIndex = 0

        for (i in bahudas.indices) {
            if (isPlayable) {
                break
            }
            bahudaIndex = i
            if (bahudas[bahudaIndex] == null) {
                continue
            }

            for (j in daihudas.indices) {
                if (isPlayable) {
                    break
                }
                daihudaIndex = j
                if (daihudas[daihudaIndex] == null) {
                    continue
                }

                isPlayable = isBahudaPlaybale(
                    bahudas[bahudaIndex]!!,
                    daihudas[daihudaIndex]!!
                )
            }
        }

        return Triple(isPlayable, bahudaIndex, daihudaIndex)
    }

    fun isToReset(viewModel: GameViewModel): Boolean {
        //盤面に出せるカードが無ければリセットが必要になる
        val (isPlayable2, _, _) = searchPlayableCard(viewModel.bahudas2p.value, viewModel.daihudas.value)
        val (isPlayable1, _, _) = searchPlayableCard(viewModel.bahudas1p.value, viewModel.daihudas.value)
        return (!isPlayable2 && !isPlayable1)
    }

    fun gameWinner(viewModel: GameViewModel): String? {
        var gameWinner: String? = null
        if (viewModel.bahudas2p.value.all { it == null }) {
            gameWinner = "コンピュータ"
        }
        else if (viewModel.bahudas1p.value.all { it == null }) {
            gameWinner = "あなた"
        }
        return gameWinner
    }
}