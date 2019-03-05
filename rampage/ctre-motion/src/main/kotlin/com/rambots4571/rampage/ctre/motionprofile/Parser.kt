package com.rambots4571.rampage.ctre.motionprofile

import com.ctre.phoenix.motion.TrajectoryPoint
import java.io.File
import java.lang.NumberFormatException
import java.util.*

class Parser(private var ticksPerUnit: Double) {
    var positionCol = 0
    var velocityCol = 1
    var timeDurationCol = 2
    fun getPoints(
        filePath: String): Queue<TrajectoryPoint> {
        val sequence: Queue<TrajectoryPoint> = LinkedList<TrajectoryPoint>()
        File(filePath).forEachLine {
            val values = it.split(", ")
            val point = TrajectoryPoint()
            try {
                point.position = values[positionCol].toDouble() * ticksPerUnit
                point.velocity = values[velocityCol].toDouble() * ticksPerUnit / 10
                point.timeDur = values[timeDurationCol].toInt()
            } catch (e: NumberFormatException) {
                println("couldn't parse '$it' into a number, skipping line..." )
            }
            point.headingDeg = 0.0
            point.profileSlotSelect0 = 0
            point.profileSlotSelect1 = 0
            sequence.add(point)
        }
        return sequence
    }
}