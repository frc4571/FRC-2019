package org.usfirst.frc.team4571.robot.commands.autonomous

import edu.wpi.first.wpilibj.command.Command
import org.usfirst.frc.team4571.robot.MotionProfile
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem

class FollowPath(pathName: String) : Command() {
    init {
        requires(DriveSystem)
        MotionProfile.path = pathName
    }

    override fun initialize() {
        DriveSystem.setTalonsFactoryDefault()
        DriveSystem.resetEncoders()
        DriveSystem.configMPGains()
        DriveSystem.configTrajPointPeriod()
    }

    override fun execute() {
        MotionProfile.control()
        val output = MotionProfile.setValue
        DriveSystem.setMPOutput(output.value)
        MotionProfile.startProfile()
    }

    override fun isFinished(): Boolean = MotionProfile.end

    override fun end() {
        DriveSystem.setPercentOuput()
        DriveSystem.resetEncoders()
    }
}