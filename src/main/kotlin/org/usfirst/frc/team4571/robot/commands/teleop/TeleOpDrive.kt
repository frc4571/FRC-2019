package org.usfirst.frc.team4571.robot.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.Constants
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

object TeleOpDrive : Command() {
    private var prevLeftPos = 0.0
    private var prevRightPos = 0.0

    init {
        requires(DriveSystem)
    }

    private fun log() {
        SmartDashboard.putNumber("Right Distance",
                                 DriveSystem.rightDistance(Constants.Unit.Feet))
        SmartDashboard.putNumber("Left Distance",
                                 DriveSystem.leftDistance(Constants.Unit.Feet))

        val currentLeftPos = DriveSystem.leftDistance(Constants.Unit.Feet)
        val currentRightPos = DriveSystem.rightDistance(Constants.Unit.Feet)

        val leftSpeed = (currentLeftPos - prevLeftPos) / Constants.period
        val rightSpeed = (currentRightPos - prevLeftPos) / Constants.period

        SmartDashboard.putNumber("Left Velocity (ft/s)", leftSpeed)
        SmartDashboard.putNumber("Right Velocity (ft/s)", rightSpeed)

        prevLeftPos = currentLeftPos
        prevRightPos = currentRightPos
    }

    override fun execute() {
        DriveSystem.drive(Robot.leftStick.yAxis, Robot.rightStick.yAxis)
        log()
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        DriveSystem.stop()
        DriveSystem.resetEncoders()
        prevLeftPos = 0.0
        prevRightPos = 0.0
    }
}