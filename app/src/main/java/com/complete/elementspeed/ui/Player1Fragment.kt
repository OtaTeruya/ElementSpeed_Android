package com.complete.elementspeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.complete.elementspeed.util.Judge
import kotlin.math.min

class Player1Fragment(private val callback: MyCallback) : Fragment() {
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    Player1FragmentScreen()
                }
            }
        }
    }

    private suspend fun tryToPlay(bahudaIndex: Int, daihudaIndex: Int) {
        val bahuda = viewModel.bahudas1p.value[bahudaIndex]
        val daihuda = viewModel.daihudas.value[daihudaIndex]

        if (bahuda==null || daihuda==null) {
            return
        }

        if (Judge().isBahudaPlaybale(bahuda, daihuda)) {
            callback.playBahuda(1, bahudaIndex, daihudaIndex)
        }
        else {
            callback.failToPlayBahuda()
        }
    }

    private suspend fun swipeCard(start: Offset?, end: Offset?, width: Int) {
        if (start==null || end==null) {
            return
        }

        val bahudas1p = viewModel.bahudas1p.value
        val bahudaLength = bahudas1p.size
        val bahudaIndex = min(
            (start.x / (width.toDouble()/bahudaLength.toDouble())).toInt(),
            bahudaLength-1 //IndexOutOfRangeを防ぐ
        )

        val horizontalVector = end.x - start.x

        if (horizontalVector < -10) {//左に動いた場合
            tryToPlay(bahudaIndex, 0)
        }
        if (horizontalVector > 10) {//右に動いた場合
            tryToPlay(bahudaIndex, 1)
        }
    }

    @Composable
    fun Player1FragmentScreen() {
        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        val bahudas1p by viewModel.bahudas1p.collectAsState()
        val tehudaNumber1p by viewModel.tehudaNumber1p.collectAsState()

        var startTapPosition by remember {mutableStateOf<Offset?>(null)}
        var endTapPosition by remember {mutableStateOf<Offset?>(null)}
        var rowWidth by remember {mutableIntStateOf(0)}
        var isDragEnd by remember { mutableStateOf(false)}

        LaunchedEffect(isDragEnd) {
            if (isDragEnd) {
                isDragEnd = false
                swipeCard(startTapPosition, endTapPosition, rowWidth)
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End
        ){
            Text(
                text = tehudaNumber1p.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates -> rowWidth = coordinates.size.width }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                // ドラッグ開始時の座標を保存
                                startTapPosition = offset
                            },
                            onDrag = { change, _ ->
                                endTapPosition = change.position
                            },
                            onDragEnd = {
                                isDragEnd = true
                            }
                        )
                    },
            ) {
                for (i in bahudas1p.indices) {
                    Card(bahudas1p[i])
                }
            }
        }
    }
}