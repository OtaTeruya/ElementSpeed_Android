package com.complete.elementspeed

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class ComputerPlayer(private val viewModel: GameViewModel, private val callback: MyCallback) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
    private var job: Job? = null
    private val INTERVAL_MILLIS = 3000L

    fun startRepeatingTask() {
        stopRepeatingTask()//二つ同時には動かないように、一応止めておく

        job = coroutineScope.launch {
            repeatEvery() {
                withContext(Dispatchers.Main) {
                    tryToPlay()
                }
            }
        }
    }

    fun stopRepeatingTask() {
        job?.cancel()
    }

    // 定期的に処理を実行する関数
    private suspend fun repeatEvery(action: suspend () -> Unit) {
        while (true) {
            delay((INTERVAL_MILLIS * Random.nextDouble(0.8, 1.2)).toLong())
            action()
        }
    }

    // 実行する処理
    private fun tryToPlay() {
        //出せるカードがあればプレイする
        val bahudas2p = viewModel.getBahudas2p()
        val daihudas = viewModel.getDaihudas()
        println(bahudas2p.toString())

        var isPlayable = false
        var bahudaIndex = 0
        var daihudaIndex = 0
        val judge = Judge()
        for (i in bahudas2p.indices) {
            if (isPlayable) {
                break
            }
            bahudaIndex = i

            for (j in daihudas.indices) {
                if (isPlayable) {
                    break
                }
                daihudaIndex = j

                isPlayable = judge.isBahudaPlaybale(
                    bahudas2p[bahudaIndex],
                    daihudas[daihudaIndex]
                )
            }
        }

        if (!isPlayable) {
            //プレイできるカードが無ければ
            return
        }

        callback.playBahuda(2, bahudaIndex, daihudaIndex)
    }
}