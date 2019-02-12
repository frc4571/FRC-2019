package org.usfirst.frc.team4571.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem;

public class TeleOpDrive extends Command {
    private static TeleOpDrive instance;
    private DriveSystem drivetrain;

    private TeleOpDrive() {
        drivetrain = DriveSystem.getInstance();
        requires(drivetrain);
    }

    public static TeleOpDrive getInstance() {
        if (instance == null) {
            instance = new TeleOpDrive();
        }
        return instance;
    }

    private void log() {
        SmartDashboard.putNumber(
                "Left encoder", drivetrain.getLeftEncoderTick());
        SmartDashboard.putNumber(
                "Left encoder", drivetrain.getRightEncoderTick());
        SmartDashboard.putNumber(
                "Left Speed", drivetrain.getLeftVelocity(Constants.Unit.Feet));
        SmartDashboard.putNumber(
                "Right Speed", drivetrain
                        .getRightVelocity(Constants.Unit.Feet));
    }

    @Override
    protected void execute() {
        drivetrain.drive(
                Robot.leftStick.getYAxis(), Robot.rightStick.getYAxis());
        log();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivetrain.stop();
        drivetrain.resetEncoders();
    }
}