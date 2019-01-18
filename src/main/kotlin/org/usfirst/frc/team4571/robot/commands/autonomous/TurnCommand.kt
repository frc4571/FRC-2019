package org.usfirst.frc.team4571.robot.commands.autonomous

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

class TurnCommand(private val angle: Double) : Command() {
    init {
        requires(DriveSystem)
    }

    override fun initialize() {
        DriveSystem.resetGyro()
        DriveSystem.turnToAngle(angle)
        SmartDashboard.putData(DriveSystem.turnController)
    }

    override fun execute() {
        SmartDashboard.putNumber("Angle", DriveSystem.heading)
    }

    override fun isFinished(): Boolean = DriveSystem.isOnAngle

    override fun end() {
        DriveSystem.turnController.disable()
        DriveSystem.stop()
    }
}