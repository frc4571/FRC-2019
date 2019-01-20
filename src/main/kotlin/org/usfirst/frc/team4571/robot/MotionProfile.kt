package org.usfirst.frc.team4571.robot

import com.ctre.phoenix.motion.MotionProfileStatus
import com.ctre.phoenix.motion.SetValueMotionProfile
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.Timer.*
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem
import java.io.File
import java.io.FileNotFoundException
import java.util.*

data class Point(val position: Double, val velocity: Double, val delta: Double)

data class Gains(val kP: Double, val kI: Double, val kD: Double,
                 val kF: Double, val kIzone: Int, val maxOutput: Double)

fun getPoints(fileName: String, dir: String = "home/lvuser/deploy/paths",
              skipFirstLine: Boolean = false): ArrayList<Point> {
    val file = File(dir + fileName)
    val list = ArrayList<Point>()
    try {
        val reader = Scanner(file)
        if (skipFirstLine) reader.nextLine()
        while (reader.hasNextLine()) {
            val values = reader.nextLine().split(", ")
            list.add(Point(values[0].toDouble(), values[1].toDouble(),
                           values[2].toDouble()))
        }
    } catch (e: FileNotFoundException) {
        println("can't find file")
        println(e.stackTrace)
    }
    return list
}

class PeriodicRunnable(private val motor: WPI_TalonSRX) : Runnable {
    override fun run() = motor.processMotionProfileBuffer()
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
    private var pos = 0.0
    private var vel = 0.0
    private var heading = 0.0

    private val leftTalon = DriveSystem.leftMaster
    private val rightTalon = DriveSystem.rightMaster

    private var state = 0
    private var loopTimeout = -1

    private var start = false

    private var setValue = SetValueMotionProfile.Disable

    private val minPoints = 5
    private var loopsTimeout = 10

    private val leftNotifier = Notifier(PeriodicRunnable(leftTalon))
    private val rightNotifier = Notifier(PeriodicRunnable(rightTalon))

    init {
        leftTalon.changeMotionControlFramePeriod(5)
        rightTalon.changeMotionControlFramePeriod(5)
        leftNotifier.startPeriodic(0.005)
        rightNotifier.startPeriodic(0.005)
    }

    fun reset() {
        leftTalon.clearMotionProfileTrajectories()
        rightTalon.clearMotionProfileTrajectories()

    }
}