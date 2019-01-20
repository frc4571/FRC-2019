package org.usfirst.frc.team4571.robot

import com.ctre.phoenix.motion.MotionProfileStatus
import com.ctre.phoenix.motion.SetValueMotionProfile
import com.ctre.phoenix.motion.TrajectoryPoint
import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer.getFPGATimestamp
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

data class Gains(val kP: Double, val kI: Double, val kD: Double,
                 val kF: Double, val kIzone: Int, val maxOutput: Double)

fun getPoints(fileName: String, dir: String = "home/lvuser/deploy/paths",
              skipFirstLine: Boolean = false): ArrayList<TrajectoryPoint> {
    val file = File(dir + fileName)
    val list = ArrayList<TrajectoryPoint>()
    val point = TrajectoryPoint()
    try {
        val reader = Scanner(file)
        if (skipFirstLine) reader.nextLine()
        while (reader.hasNextLine()) {
            val values = reader.nextLine().split(", ")
            point.position = values[0].toDouble() *
                    Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET
            point.velocity = values[1].toDouble() *
                    Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET / 10
            point.headingDeg = 0.0
            point.profileSlotSelect0 = 0
            point.profileSlotSelect1 = 0
            point.timeDur = values[2].toInt()
            list.add(point)
        }
    } catch (e: FileNotFoundException) {
        println("can't find file")
        println(e.stackTrace)
    }
    return list
}

object Instrumentation {
    val timeout = 0.0
    var count = 0
    val table = arrayOf(" Dis ", " En ", "Hold ")

    fun onUnderrun() = System.out.format("%s\n", "UNDERRUN")

    fun onNoProgress() = System.out.format("%s\n", "NOPROGRESS")

    fun strOutputEnable(sv: SetValueMotionProfile): String {
        return when {
            sv.value > 3 -> "inval"
            else -> table[sv.value]
        }
    }

    fun process(status: MotionProfileStatus, pos: Double, vel: Double,
                heading: Double) {
        val now = getFPGATimestamp()

        if (now - timeout > 0.2) {
            if (--count <= 0) {
                count = 8
                System.out.format("%-9s\t", "outEn")
                System.out.format("%-9s\t", "topCnt")
                System.out.format("%-9s\t", "topRem")
                System.out.format("%-9s\t", "btmCnt")
                System.out.format("%-9s\t", "IsValid")
                System.out.format("%-9s\t", "HasUnder")
                System.out.format("%-9s\t", "IsUnder")
                System.out.format("%-9s\t", "IsLast")
                System.out.format("%-9s\t", "targPos")
                System.out.format("%-9s\t", "targVel")
                System.out.format("%-9s\t", "SlotSel0")
                System.out.format("%-9s\t", "timeDurMs")

                System.out.format("\n")
            }
            System.out.format("%-9s\t", strOutputEnable(status.outputEnable))
            System.out.format("%-9s\t", status.topBufferCnt)
            System.out.format("%-9s\t", status.topBufferRem)
            System.out.format("%-9s\t", status.btmBufferCnt)
            System.out.format("%-9s\t", if (status.activePointValid) "1" else "")
            System.out.format("%-9s\t", if (status.hasUnderrun) "1" else "")
            System.out.format("%-9s\t", if (status.isUnderrun) "1" else "")
            System.out.format("%-9s\t", if (status.isLast) "1" else "")
            System.out.format("%-9s\t", pos)
            System.out.format("%-9s\t", vel)
            System.out.format("%-9s\t", status.profileSlotSelect)
            System.out.format("%-9s\t", status.timeDurMs)

            System.out.format("\n")
        }
    }
}

object MotionProfile {
    private val status = MotionProfileStatus()

    private val leftTalon = DriveSystem.leftMaster
    private val rightTalon = DriveSystem.rightMaster

    private var state = 0
    private var loopTimeout = -1

    private var start = false
    var end = false

    var setValue = SetValueMotionProfile.Disable

    private const val minPoints = 5
    private var numOfLoopsTimeout = 10

    var path: String = ""

    private object PeriodicRunnable : Runnable {
        override fun run() {
            leftTalon.processMotionProfileBuffer()
            rightTalon.processMotionProfileBuffer()
        }
    }

    private val notifier = Notifier(PeriodicRunnable)

    init {
        leftTalon.changeMotionControlFramePeriod(5)
        rightTalon.changeMotionControlFramePeriod(5)
        notifier.startPeriodic(0.005)
    }

    fun reset() {
        leftTalon.clearMotionProfileTrajectories()
        rightTalon.clearMotionProfileTrajectories()
        state = 0
        loopTimeout = -1
        start = false
    }

    fun control() {
        when {
            loopTimeout < 0 -> { // does nothing timeout disabled
            }
            loopTimeout == 0 -> Instrumentation.onNoProgress()
            else -> --loopTimeout
        }
        if (leftTalon.controlMode != ControlMode.MotionProfile &&
                rightTalon.controlMode != ControlMode.MotionProfile) {
            state = 0
            loopTimeout = -1
        } else when (state) {
            0 -> if (start) {
                start = false
                setValue = SetValueMotionProfile.Disable
                startFilling()
                state = 1
                loopTimeout = numOfLoopsTimeout
            }
            1 -> if (status.btmBufferCnt > minPoints) {
                setValue = SetValueMotionProfile.Enable
                state = 2
                loopTimeout = numOfLoopsTimeout
            }
            2 -> {
                if (!status.isUnderrun) {
                    loopTimeout = numOfLoopsTimeout
                }
                if (status.activePointValid && status.isLast) {
                    setValue = SetValueMotionProfile.Hold
                    state = 0
                    loopTimeout = -1
                    end = true
                }
            }
        }
    }

    private fun startFilling() {
        val leftPoints = getPoints("${path}_left.csv")
        val rightPoints = getPoints("${path}_right.csv")
        startFilling(leftPoints, rightPoints)
    }

    private fun startFilling(leftPoints: ArrayList<TrajectoryPoint>,
                             rightPoints: ArrayList<TrajectoryPoint>) {
        if (status.hasUnderrun) {
            Instrumentation.onUnderrun()
            leftTalon.clearMotionProfileHasUnderrun(0)
            rightTalon.clearMotionProfileHasUnderrun(0)
        }
        leftTalon.clearMotionProfileTrajectories()
        rightTalon.clearMotionProfileTrajectories()

        for (i in 0..(leftPoints.size - 1)) {
            if (i == 0) {
                leftPoints[i].zeroPos = true
                rightPoints[i].zeroPos = true
            }
            if (i + 1 == leftPoints.size) {
                leftPoints[i].isLastPoint = true
                rightPoints[i].isLastPoint = true
            }
            leftTalon.pushMotionProfileTrajectory(leftPoints[i])
            rightTalon.pushMotionProfileTrajectory(rightPoints[i])
        }
    }

    fun startProfile() {
        start = true
    }
}