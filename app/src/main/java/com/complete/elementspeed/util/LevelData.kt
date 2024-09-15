package com.complete.elementspeed.util

class LevelData {
    val LEVEL_SIZE = 8

    private val levelTextList = listOf(
        "かんたん", "かんたん＋", "ふつう", "ふつう＋", "むずかしい", "むずかしい＋", "おに", "おに＋"
    )
    fun getText(level: Int): String {
        return levelTextList[level]
    }

    private val elementNumMaxList = listOf(10, 10, 20, 20, 40, 40, 118, 118)
    fun getElementPool(level: Int): List<Int> {
        return (1..elementNumMaxList[level]).toList()
    }
    fun getElementNumMax(level: Int): Int {
        return elementNumMaxList[level]
    }

    private val judgeRuleList = listOf(
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = false),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = false),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = false),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = false),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = true),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = true),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = true),
        JudgeRule(isNumberUsed = true, isGroupUsed = true, isTextLengthUsed = true)
    )
    fun getJudgeRule(level: Int): JudgeRule {
        return judgeRuleList[level]
    }

    private val hintIsNeededList = listOf(true, false, true, false, true, false, true, false)
    fun getHintIsNeeded(level: Int): Boolean {
        return hintIsNeededList[level]
    }
}