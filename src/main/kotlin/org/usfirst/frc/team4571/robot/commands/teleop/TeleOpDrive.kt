package org.usfirst.frc.team4571.robot.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

object TeleOpDrive : Command() {
    init {
        requires(DriveSystem)
    }

    private fun log() {
        SmartDashboard.putNumber("Left Joystick", Robot.leftStick.yAxis)
        SmartDashboard.putNumber("Right Joystick", Robot.rightStick.yAxis)

//        SmartDashboard.putNumber("Left Follower Motor Output",
//                                 DriveSystem.leftFollowerSpeed)
//        SmartDashboard.putNumber("Left Master Motor Output",
//                                 DriveSystem.leftMasterSpeed)
//        SmartDashboard.putNumber("Right Master Motor Output",
//                                 DriveSystem.rightMasterSpeed)
//        SmartDashboard.putNumber("Right Follower Motor Output",
//                                 DriveSystem.rightFollowerSpeed)

        SmartDashboard.putNumber("Right encoder Tick",
                                 DriveSystem.rightEncoderTick.toDouble())
        SmartDashboard.putNumber("Left Encoder Tick",
                                 DriveSystem.leftEncoderTick.toDouble())

        SmartDashboard.putNumber("Right Distance", DriveSystem.rightDistance)
        SmartDashboard.putNumber("Left Distance", DriveSystem.leftDistance)
    }

    override fun execute() {
        DriveSystem.drive(Robot.leftStick.yAxis, Robot.rightStick.yAxis)
        log()
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        DriveSystem.stop()
        DriveSystem.resetEncoders()
    }
}