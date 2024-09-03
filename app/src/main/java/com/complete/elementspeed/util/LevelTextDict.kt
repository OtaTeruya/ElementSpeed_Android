package com.complete.elementspeed.util

class LevelTextDict {
    private val levelTextDict = mapOf(
        0 to "かんたん",
        1 to "ふつう"
    )

    fun getText(difficulty: Int): String {
        if (difficulty >= levelTextDict.size || difficulty < 0) {
            return ""
        }
        return levelTextDict[difficulty]!!
    }
}