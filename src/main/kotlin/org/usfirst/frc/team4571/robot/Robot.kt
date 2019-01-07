package org.usfirst.frc.team4571.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser

class Robot : TimedRobot() {
    private val autoChooser = SendableChooser<Command>()
    private var autoCommand: Command? = null

    companion object {
        const val period = TimedRobot.kDefaultPeriod
    }

    override fun robotInit() {}

    override fun autonomousInit() {
        autoCommand = autoChooser.selected
        autoCommand?.start()
    }

    override fun autonomousPeriodic() = Scheduler.getInstance().run()

    override fun teleopInit() {
        autoCommand?.cancel()
    }

    override fun teleopPeriodic() {}
}