package org.usfirst.frc.team4571.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import org.usfirst.frc.team4571.robot.commands.teleop.TeleOpDrive

class Robot : TimedRobot() {
    private val autoChooser = SendableChooser<Command>()
    private var autoCommand: Command? = null

    companion object {
        const val period = TimedRobot.kDefaultPeriod
        val leftStick = DriveStick(Constants.Controllers.LEFT_STICK)
        val rightStick = DriveStick(Constants.Controllers.RIGHT_STICK)
    }

    override fun robotInit() {}

    override fun autonomousInit() {
        autoCommand = autoChooser.selected
        autoCommand?.start()
    }

    override fun autonomousPeriodic() = Scheduler.getInstance().run()

    override fun teleopInit() {
        autoCommand?.cancel()
        Scheduler.getInstance().add(TeleOpDrive)
    }

    override fun teleopPeriodic() {
        Scheduler.getInstance().run()
    }
}