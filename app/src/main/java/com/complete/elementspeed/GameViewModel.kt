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
    fun getBahudas2p(): List<Element> {
        return bahudas2p.value!!
    }

    fun updateTehudas2p(value: List<Element>) {
        tehudas2p.value = value
    }
    fun getTehudas2p(): List<Element> {
        return tehudas2p.value!!
    }

    fun updateTehudaNumber2p(value: Int) {
        tehudaNumber2p.value = value
    }
    fun getTehudaNumber2p(): Int {
        return tehudaNumber2p.value!!
    }

    fun updateBahudas1p(value: List<Element>) {
        bahudas1p.value = value
    }
    fun getBahudas1p(): List<Element> {
        return bahudas1p.value!!
    }

    fun updateTehudas1p(value: List<Element>) {
        tehudas1p.value = value
    }
    fun getTehudas1p(): List<Element> {
        return tehudas2p.value!!
    }

    fun updateTehudaNumber1p(value: Int) {
        tehudaNumber1p.value = value
    }
    fun getTehudaNumber1p(): Int {
        return tehudaNumber2p.value!!
    }

    fun updateDaihudas(value: List<Element>) {
        daihudas.value = value
    }
    fun getDaihudas(): List<Element> {
        return daihudas.value!!
    }
}