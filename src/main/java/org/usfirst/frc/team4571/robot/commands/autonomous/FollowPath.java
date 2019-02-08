package org.usfirst.frc.team4571.robot.commands.autonomous;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.rambots4571.rampage.ctre.hardware.LazyTalonSRX;
import com.rambots4571.rampage.ctre.motionprofile.Parser;
import com.rambots4571.rampage.ctre.motionprofile.Profile;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.Robot;

import java.util.ArrayList;

public class FollowPath extends Command {
    private Profile profile;

    public FollowPath(String pathName) {
        requires(Robot.DRIVE_SYSTEM);
        LazyTalonSRX[] talons = Robot.DRIVE_SYSTEM.getTalonMasters();
        Parser parser =
                new Parser(Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET);
        ArrayList<TrajectoryPoint> left =
                parser.getPoints(Constants.paths.dir + pathName +
                                 Constants.paths.leftSuffix, true);
        ArrayList<TrajectoryPoint> right =
                parser.getPoints(Constants.paths.dir + pathName +
                                 Constants.paths.rightSuffix, false);
        profile = new Profile(left, right, talons[0], talons[1]);
    }

    @Override
    protected void initialize() {
        Robot.DRIVE_SYSTEM.configMPGains();
        profile.execute();
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber(
                "left distance", Robot.DRIVE_SYSTEM
                        .getLeftDistance(Constants.Unit.Feet));
        SmartDashboard.putNumber(
                "right distance", Robot.DRIVE_SYSTEM
                        .getRightDistance(Constants.Unit.Feet));
    }

    @Override
    protected boolean isFinished() {
        return profile.isFinished();
    }

    @Override
    protected void end() {
        Robot.DRIVE_SYSTEM.stop();
    }

    @Override
    protected void interrupted() {
        profile.onInterrupt();
    }
}