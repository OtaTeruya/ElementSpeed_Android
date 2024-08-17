package com.complete.elementspeed

import kotlin.math.abs

class Judge {
    private fun isBahudaPlaybale(bahuda: Element, daihuda: Element): Boolean {
        //原子番号が一つ違いの場合
        if (abs(bahuda.elementNumber - daihuda.elementNumber) == 1) {
            return true
        }

        //同属の場合
        if (bahuda.elementGroup == daihuda.elementGroup) {
            return true
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

        return Triple(isPlayable, daihudaIndex, bahudaIndex)
    }
}