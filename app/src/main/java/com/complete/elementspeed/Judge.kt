package com.complete.elementspeed

import kotlin.math.abs

class Judge {
    fun isBahudaPlaybale(bahuda: Element, daihuda: Element): Boolean {
        //原子番号が一番違いの場合
        if (abs(bahuda.elementNumber - daihuda.elementNumber) == 1) {
            return true
        }

        //同属の場合
        if (bahuda.elementGroup == daihuda.elementGroup) {
            return true
        }

        return false
    }
}