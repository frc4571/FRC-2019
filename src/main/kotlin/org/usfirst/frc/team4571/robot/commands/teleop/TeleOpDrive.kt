package org.usfirst.frc.team4571.robot.commands.teleop

import edu.wpi.first.wpilibj.command.Command
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

object TeleOpDrive : Command() {
    init {
        requires(DriveSystem)
    }

    override fun execute() =
        DriveSystem.drive(Robot.leftStick.yAxis, Robot.rightStick.yAxis)

    override fun isFinished(): Boolean = false

    override fun end() = DriveSystem.stop()
}