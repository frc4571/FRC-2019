package com.rambots4571.rampage.ctre.motionprofile

import com.ctre.phoenix.motion.TrajectoryPoint
import edu.wpi.first.wpilibj.DriverStation
import java.io.File
import java.util.*

class Parser(private val ticksPerUnit: Double) {
    var positionCol = 0
    var velocityCol = 1
    var timeDurationCol = 2

    fun getPoints(filePath: String): Queue<TrajectoryPoint> {
        val sequence: Queue<TrajectoryPoint> = ArrayDeque<TrajectoryPoint>(600)
        File(filePath).forEachLine {
            val values = it.split(", ")
            val point = TrajectoryPoint()
            try {
                point.position = values[positionCol].toDouble() * ticksPerUnit
                point.velocity =
                    values[velocityCol].toDouble() * ticksPerUnit / 10
                point.timeDur = values[timeDurationCol].toInt()
            } catch (e: NumberFormatException) {
                println("couldn't parse '$it' into a number, skipping line...")
            } catch (e: IndexOutOfBoundsException) {
                DriverStation.reportWarning(
                        "could not parse $values because column values were " +
                        "not specified correctly, \n" +
                        "position column = $positionCol \n" +
                        "velocity column = $velocityCol \n" +
                        "time duration column = $timeDurationCol \n" +
                        "there are only ${values.size} columns", false)
            }
            point.headingDeg = 0.0
            point.profileSlotSelect0 = 0
            point.profileSlotSelect1 = 0
            sequence.add(point)
        }
        return sequence
    }
}