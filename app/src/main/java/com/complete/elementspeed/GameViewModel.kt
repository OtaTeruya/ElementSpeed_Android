package com.complete.elementspeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val bahudas2p: MutableLiveData<List<Element>> = MutableLiveData(emptyList())
    val tehudas2p: MutableLiveData<List<Element>> = MutableLiveData(emptyList())
    val tehudaNumber2p = MutableLiveData(0)
    val bahudas1p: MutableLiveData<List<Element>> = MutableLiveData(emptyList())
    val tehudas1p: MutableLiveData<List<Element>> = MutableLiveData(emptyList())
    val tehudaNumber1p = MutableLiveData(0)
    val daihudas: MutableLiveData<List<Element>> = MutableLiveData(emptyList())

    fun updateBahudas2p(value: List<Element>) {
        bahudas2p.value = value
    }

    fun updateTehudas2p(value: List<Element>) {
        tehudas2p.value = value
    }

    fun updateTehudaNumber2p(value: Int) {
        tehudaNumber2p.value = value
    }

    fun updateBahudas1p(value: List<Element>) {
        bahudas1p.value = value
    }

    fun updateTehudas1p(value: List<Element>) {
        tehudas1p.value = value
    }

    fun updateTehudaNumber1p(value: Int) {
        tehudaNumber1p.value = value
    }

    fun updateDaihudas(value: List<Element>) {
        daihudas.value = value
    }
}