package org.usfirst.frc.team4571.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.usfirst.frc.team4571.robot.commands.autonomous.FollowPath
import org.usfirst.frc.team4571.robot.commands.autonomous.TurnCommand
import org.usfirst.frc.team4571.robot.commands.teleop.TeleOpDrive

class Robot : TimedRobot(Constants.ROBOT_PERIOD) {
    private val autoChooser = SendableChooser<Command>()
    private var autoCommand: Command? = null

    companion object {
        val leftStick = DriveStick(Constants.Controllers.LEFT_STICK)
        val rightStick = DriveStick(Constants.Controllers.RIGHT_STICK)
    }

    override fun robotInit() {
        autoChooser.addOption("Turn 90 Degrees", TurnCommand(90.0))
        autoChooser.addOption("Run test path", FollowPath("testpath"))
        SmartDashboard.putData("Auto Chooser", autoChooser)
    }

    override fun disabledInit() = Scheduler.getInstance().removeAll()

    override fun disabledPeriodic() = Scheduler.getInstance().run()

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