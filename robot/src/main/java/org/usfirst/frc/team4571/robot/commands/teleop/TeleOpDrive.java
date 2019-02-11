package org.usfirst.frc.team4571.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.Robot;

public class TeleOpDrive extends Command {
    private static final TeleOpDrive instance = new TeleOpDrive();

    private TeleOpDrive() {
        requires(Robot.DRIVE_SYSTEM);
    }

    public static TeleOpDrive getInstance() {
        return instance;
    }

    private void log() {
        SmartDashboard.putNumber(
                "Left encoder", Robot.DRIVE_SYSTEM.getLeftEncoderTick());
        SmartDashboard.putNumber(
                "Left encoder", Robot.DRIVE_SYSTEM.getRightEncoderTick());
        SmartDashboard.putNumber(
                "Left Speed",
                Robot.DRIVE_SYSTEM.getLeftVelocity(
                        Constants.Unit.Feet));
        SmartDashboard.putNumber(
                "Right Speed",
                Robot.DRIVE_SYSTEM.getRightVelocity(
                        Constants.Unit.Feet));
    }

    @Override
    protected void execute() {
        Robot.DRIVE_SYSTEM.drive(
                Robot.leftStick.getYAxis(), Robot.rightStick.getYAxis());
        log();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.DRIVE_SYSTEM.stop();
        Robot.DRIVE_SYSTEM.resetEncoders();
    }
}