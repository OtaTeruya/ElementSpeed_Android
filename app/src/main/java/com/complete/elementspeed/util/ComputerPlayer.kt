package com.complete.elementspeed.util

import com.complete.elementspeed.ui.GameViewModel
import com.complete.elementspeed.ui.MyCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class ComputerPlayer(
    private val viewModel: GameViewModel, private val callback: MyCallback, private val judgeRule: JudgeRule) {
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
    private suspend fun tryToPlay() {
        //出せるカードがあればプレイする
        val bahudas2p = viewModel.bahudas2p.value
        val daihudas = viewModel.daihudas.value
        val (isPlayable, bahudaIndex, daihudaIndex) = Judge(judgeRule).searchPlayableCard(bahudas2p, daihudas)

        if (!isPlayable) {
            //プレイできるカードが無ければ
            return
        }

        callback.playBahuda(2, bahudaIndex, daihudaIndex)
    }
}