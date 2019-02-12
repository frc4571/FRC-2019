package com.rambots4571.rampage.led

import edu.wpi.first.wpilibj.Spark

class REVBlinkin(port: Int) : Spark(port) {
    fun setPattern(pattern: Pair<Int, Double>) {
        expiration = pattern.first * 10e-4
        set(pattern.second)
    }
}