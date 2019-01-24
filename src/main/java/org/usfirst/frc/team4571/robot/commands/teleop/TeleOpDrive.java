package org.usfirst.frc.team4571.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.Robot;

public class TeleOpDrive extends Command {
    private static final TeleOpDrive instance = new TeleOpDrive();
    private double prevLeftPos = 0;
    private double prevRightPos = 0;

    private TeleOpDrive() {
        requires(Robot.DRIVE_SYSTEM);
    }

    public static TeleOpDrive getInstance() {
        return instance;
    }

    private void log() {
        double currentLeftPos = Robot.DRIVE_SYSTEM.getLeftDistance(
                Constants.Unit.Feet);
        double currentRightPos = Robot.DRIVE_SYSTEM.getRightDistance(
                Constants.Unit.Feet);
        SmartDashboard.putNumber("Left encoder", Robot.DRIVE_SYSTEM.getLeftEncoderTick());
        SmartDashboard.putNumber("Left encoder", Robot.DRIVE_SYSTEM.getRightEncoderTick());

        double leftSpeed = (currentLeftPos - prevLeftPos) / Constants.period;
        double rightSpeed = (currentRightPos - prevRightPos) / Constants.period;

        SmartDashboard.putNumber("left velocity (ft/s)", leftSpeed);
        SmartDashboard.putNumber("right velocity (ft/s)", rightSpeed);

        prevLeftPos = currentLeftPos;
        prevRightPos = currentRightPos;
    }

    @Override
    protected void execute() {
        Robot.DRIVE_SYSTEM.drive(Robot.leftStick.getYAxis(), Robot.rightStick.getYAxis());
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
        prevLeftPos = 0;
        prevRightPos = 0;
    }
}