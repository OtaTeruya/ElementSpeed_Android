package com.complete.elementspeed.util

import com.complete.elementspeed.data.Element
import com.complete.elementspeed.data.ElementData

class ElementProvider(level: Int) {
    private var poolOfElementNumbers: List<Int> = LevelData().getElementPool(level)
    private val elementData = ElementData()

    fun generateTehudas(): List<Element> {
        val tehudas: MutableList<Element> = mutableListOf()

        val maximumSize = 20
        for (elementNumber in poolOfElementNumbers.shuffled()) {
            if (tehudas.size >= maximumSize) {
                break
            }
            tehudas += elementData.getElement(elementNumber)
        }
        return tehudas
    }

    fun getRandomElement(): Element {
        return elementData.getElement(poolOfElementNumbers.random())
    }
}