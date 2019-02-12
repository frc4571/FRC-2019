package org.usfirst.frc.team4571.robot.commands.autonomous;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.rampage.ctre.motionprofile.Parser;
import com.rambots4571.rampage.ctre.motionprofile.Profile;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem;

import java.util.ArrayList;

// TODO: change the time duration of points
public class FollowPath extends Command {
    private DriveSystem drivetrain;
    private Profile profile;

    public FollowPath(String pathName) {
        drivetrain = DriveSystem.getInstance();
        requires(drivetrain);
        TalonSRX[] talons = drivetrain.getTalonMasters();
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
        drivetrain.configMPGains();
        profile.execute();
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("left distance", drivetrain
                .getLeftDistance(Constants.Unit.Feet));
        SmartDashboard.putNumber("right distance", drivetrain
                .getRightDistance(Constants.Unit.Feet));
    }

    @Override
    protected boolean isFinished() {
        return profile.isFinished();
    }

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        profile.onInterrupt();
    }
}