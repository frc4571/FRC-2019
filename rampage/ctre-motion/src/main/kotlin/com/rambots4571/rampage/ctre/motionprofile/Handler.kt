package com.rambots4571.rampage.ctre.motionprofile

import com.ctre.phoenix.motion.MotionProfileStatus
import com.ctre.phoenix.motion.SetValueMotionProfile
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.rambots4571.rampage.ctre.Constants
import edu.wpi.first.wpilibj.Notifier

internal class Handler(
    private val profile: Profile, private val leftTalon: TalonSRX,
    private val rightTalon: TalonSRX) {
    private val talons = arrayOf(leftTalon, rightTalon)
    private val executorThread: Notifier
    private val bufferThread: Notifier
    private var executionState: ExecutionState
    private var started = false
    private var pointIndex = 0
    val statuses = Array(talons.size) { MotionProfileStatus() }
    var currentMode: SetValueMotionProfile? = null
        private set
    var isFinished = false
        private set

    init {
        executionState = ExecutionState.WAITING
        bufferThread = Notifier(PeriodicBufferProcessor())
        bufferThread.startPeriodic(profile.motionControlFramePeriod * 1000.0)
        talons.forEach {
            it.changeMotionControlFramePeriod(
                    profile.motionControlFramePeriod)
            it.setStatusFramePeriod(
                    StatusFrameEnhanced.Status_10_MotionMagic,
                    Constants.Talon.trajectoryPointPeriod,
                    Constants.Talon.timeoutMs)
        }
        executorThread = Notifier(PeriodicExecutor())
    }

    enum class ExecutionState {
        WAITING, STARTED, EXECUTING
    }

    fun reset() {
        talons.forEach { it.clearMotionProfileTrajectories() }
        executionState = ExecutionState.WAITING
        started = false
    }

    fun execute() {
        executorThread.startPeriodic(0.025)
        started = true
    }

    fun onInterrupt() {
        bufferThread.stop()
        executorThread.stop()
        setMode(SetValueMotionProfile.Disable)
    }

    private fun onFinished() {
        isFinished = true
        bufferThread.stop()
        executorThread.stop()
        talons.forEach { it.clearMotionProfileTrajectories() }
    }

    fun manage() {
        fillTalons()
        updateMPStatuses()
        var readyToProgress = true
        when (executionState) {
            ExecutionState.WAITING ->
                if (started) {
                    started = false
                    setMode(SetValueMotionProfile.Disable)
                    executionState = ExecutionState.STARTED
                }
            ExecutionState.STARTED -> {
                for (status in statuses) {
                    if (status.btmBufferCnt <=
                            Constants.Talon.MIN_POINTS_IN_TALON)
                        readyToProgress = false
                }
                if (readyToProgress) {
                    setMode(SetValueMotionProfile.Enable)
                    executionState = ExecutionState.EXECUTING
                }
            }
            ExecutionState.EXECUTING -> {
                readyToProgress = true
                statuses.forEach {
                    if (!it.activePointValid || !it.isLast) {
                        readyToProgress = false
                    }
                }
                if (readyToProgress) {
                    onFinished()
                }
            }
        }
    }

    fun fillTalons() {
        if (pointIndex == 0) {
            talons.forEach {
                it.clearMotionProfileTrajectories()
                it.configMotionProfileTrajectoryPeriod(
                        profile.trajectoryPointPeriod, profile.timeoutMs)
                it.clearMotionProfileHasUnderrun(profile.timeoutMs)
            }
        }

        updateMPStatuses()
        var maxFilled = statuses[0].btmBufferCnt

        for (status in statuses) {
            if (status.topBufferCnt > maxFilled) {
                maxFilled = status.topBufferCnt
            }
        }

        var numPointsToFill = Constants.Talon.MAX_TOP_BUFFER_COUNT - maxFilled
        isFinished = false

        while (!isFinished && numPointsToFill > 0) {
            if (pointIndex >= profile.length) {
                isFinished = true
                break
            }

            val leftPoint = profile.leftProfile.remove()
            val rightPoint = profile.rightProfile.remove()

            leftPoint.zeroPos = false
            rightPoint.zeroPos = false

            if (pointIndex == 0) {
                leftPoint.zeroPos = true
                rightPoint.zeroPos = true
            }

            leftPoint.isLastPoint = false
            rightPoint.isLastPoint = false

            if (pointIndex + 1 == profile.length) {
                leftPoint.isLastPoint = true
                rightPoint.isLastPoint = true
            }

            leftTalon.pushMotionProfileTrajectory(leftPoint)
            rightTalon.pushMotionProfileTrajectory(rightPoint)

            pointIndex++
            numPointsToFill--
        }
        updateMPStatuses()
    }

    private fun updateMPStatuses() {
        for (i in 0 until talons.size) {
            talons[i].getMotionProfileStatus(statuses[i])
        }
    }

    private fun setMode(mode: SetValueMotionProfile) {
        currentMode = mode
        talons.forEach {
            it.set(ControlMode.MotionProfile, mode.value.toDouble())
        }
    }

    private inner class PeriodicBufferProcessor : Runnable {
        override fun run() {
            for (i in 0 until statuses.size)
                if (statuses[i].btmBufferCnt <
                        Constants.Talon.MAX_BTM_BUFFER_COUNT)
                    talons[i].processMotionProfileBuffer()
        }
    }

    private inner class PeriodicExecutor : Runnable {
        override fun run() = manage()
    }
}