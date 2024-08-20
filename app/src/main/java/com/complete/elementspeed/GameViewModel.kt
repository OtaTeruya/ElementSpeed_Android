package com.complete.elementspeed

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel: ViewModel() {
    private val _bahudas2p = MutableStateFlow<List<Element?>>(emptyList())
    val bahudas2p: StateFlow<List<Element?>> = _bahudas2p

    private val _tehudas2p = MutableStateFlow<List<Element?>>(emptyList())
    val tehudas2p: StateFlow<List<Element?>> = _tehudas2p

    private val _tehudaNumber2p = MutableStateFlow(0)
    val tehudaNumber2p: StateFlow<Int> = _tehudaNumber2p

    private val _bahudas1p = MutableStateFlow<List<Element?>>(emptyList())
    val bahudas1p: StateFlow<List<Element?>> = _bahudas1p

    private val _tehudas1p = MutableStateFlow<List<Element?>>(emptyList())
    val tehudas1p: StateFlow<List<Element?>> = _tehudas1p

    private val _tehudaNumber1p = MutableStateFlow(0)
    val tehudaNumber1p: StateFlow<Int> = _tehudaNumber1p

    private val _daihudas = MutableStateFlow<List<Element?>>(emptyList())
    val daihudas: StateFlow<List<Element?>> = _daihudas

    fun updateBahudas2p(value: List<Element?>) {
        _bahudas2p.value = value
    }

    fun updateTehudas2p(value: List<Element?>) {
        _tehudas2p.value = value
    }

    fun updateTehudaNumber2p(value: Int) {
        _tehudaNumber2p.value = value
    }

    fun updateBahudas1p(value: List<Element?>) {
        _bahudas1p.value = value
    }

    fun updateTehudas1p(value: List<Element?>) {
        _tehudas1p.value = value
    }

    fun updateTehudaNumber1p(value: Int) {
        _tehudaNumber1p.value = value
    }

    fun updateDaihudas(value: List<Element?>) {
        _daihudas.value = value
    }
}