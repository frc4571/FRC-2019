package com.rambots4571.deepspace.robot.command;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.rampage.ctre.motionprofile.Parser;
import com.rambots4571.rampage.ctre.motionprofile.Profile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.Queue;

public class FollowPath extends CommandBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Profile profile;

    public FollowPath(String pathName) {
        addRequirements(drivetrain);
        TalonSRX[] talons = drivetrain.getTalonMasters();
        Parser parser = new Parser(
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_FEET);
        Queue<TrajectoryPoint> left = parser.getPoints(
                Constants.Paths.dir + pathName + Constants.Paths.leftSuffix);
        Queue<TrajectoryPoint> right = parser.getPoints(
                Constants.Paths.dir + pathName + Constants.Paths.rightSuffix);
        profile = new Profile(left, right, talons[0], talons[1]);
    }

    @Override
    public void initialize() {
        drivetrain.configMPGains();
        profile.execute();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("left distance", drivetrain
                .getLeftDistance(Constants.Units.Feet));
        SmartDashboard.putNumber("right distance", drivetrain
                .getRightDistance(Constants.Units.Feet));
    }

    @Override
    public boolean isFinished() {
        return profile.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}