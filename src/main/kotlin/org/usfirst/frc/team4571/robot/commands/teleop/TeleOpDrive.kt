package org.usfirst.frc.team4571.robot.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.Constants
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

object TeleOpDrive : Command() {
    private var prevLeftDistance = 0.0
    private var prevRightDistance = 0.0

    init {
        requires(DriveSystem)
    }

    private fun log() {
        SmartDashboard.putNumber("Right Distance",
                                 DriveSystem.rightDistance(Constants.Unit.Feet))
        SmartDashboard.putNumber("Left Distance",
                                 DriveSystem.leftDistance(Constants.Unit.Feet))

        val leftSpeed = (DriveSystem.leftDistance(Constants.Unit.Feet) -
                prevLeftDistance) / Constants.period
        val rightSpeed = (DriveSystem.rightDistance(Constants.Unit.Feet) -
                prevLeftDistance) / Constants.period

        SmartDashboard.putNumber("Left Velocity (ft/s)", leftSpeed)
        SmartDashboard.putNumber("Right Velocity (ft/s)", rightSpeed)

        prevLeftDistance = DriveSystem.leftDistance(Constants.Unit.Feet)
        prevRightDistance = DriveSystem.rightDistance(Constants.Unit.Feet)
    }

    override fun execute() {
        DriveSystem.drive(Robot.leftStick.yAxis, Robot.rightStick.yAxis)
        log()
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        DriveSystem.stop()
        DriveSystem.resetEncoders()
        prevLeftDistance = 0.0
        prevRightDistance = 0.0
    }
}