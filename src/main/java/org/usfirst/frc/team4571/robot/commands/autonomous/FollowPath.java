package org.usfirst.frc.team4571.robot.commands.autonomous;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4571.robot.MotionProfile;
import org.usfirst.frc.team4571.robot.Robot;

public class FollowPath extends Command {

    public FollowPath(String fileName) {
        requires(Robot.DRIVE_SYSTEM);
        MotionProfile.INSTANCE.setPath(fileName);
    }

    @Override
    protected void initialize() {
        Robot.DRIVE_SYSTEM.setTalonsFactoryDefault();
        Robot.DRIVE_SYSTEM.resetEncoders();
        Robot.DRIVE_SYSTEM.configMPGains();
        Robot.DRIVE_SYSTEM.configTrajPointPeriod();
    }

    @Override
    protected void execute() {
        MotionProfile.INSTANCE.control();
        SetValueMotionProfile output = MotionProfile.INSTANCE.getSetValue();
        Robot.DRIVE_SYSTEM.setMPOutput(output.value);
        MotionProfile.INSTANCE.startProfile();
    }

    @Override
    protected boolean isFinished() {
        return MotionProfile.INSTANCE.getEnd();
    }

    @Override
    protected void end() {
        Robot.DRIVE_SYSTEM.setPercentOutput();
        Robot.DRIVE_SYSTEM.resetEncoders();
        MotionProfile.INSTANCE.reset();
    }
}