package com.complete.elementspeed.util

import com.complete.elementspeed.data.Element
import com.complete.elementspeed.data.ElementData

class ElementProvider(level: Int) {
    private var poolOfElementNumbers: List<Int>
    private val elementData = ElementData()

    init {
        if (level==0) {
            poolOfElementNumbers = listOf(1,2,3,4,5,6,7,8,9,10)
        }
        else {
            poolOfElementNumbers = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
        }
    }

    fun generateTehudas(): List<Element> {
        val tehudas: MutableList<Element> = mutableListOf()
        for (elementNumber in poolOfElementNumbers.shuffled()) {
            tehudas += elementData.getElement(elementNumber)
        }
        return tehudas
    }

    fun getRandomElement(): Element {
        return elementData.getElement(poolOfElementNumbers.random())
    }
}