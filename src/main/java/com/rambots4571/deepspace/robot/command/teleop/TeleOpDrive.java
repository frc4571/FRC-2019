package com.rambots4571.deepspace.robot.command.teleop;

import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleOpDrive extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public TeleOpDrive() {
        requires(drivetrain);
    }

    private void log() {
        //        SmartDashboard.putNumber(
        //                "Left encoder", drivetrain.getLeftEncoderTick());
        //        SmartDashboard.putNumber(
        //                "Left encoder", drivetrain.getRightEncoderTick());
        //        SmartDashboard.putNumber(
        //                "Left Speed", drivetrain.getLeftVelocity(Constants
        //                .Units.Feet));
        //        SmartDashboard.putNumber(
        //                "Right Speed", drivetrain
        //                        .getRightVelocity(Constants.Units.Feet));
        SmartDashboard.putNumber("left joystick", Robot.leftStick.getYAxis());
        SmartDashboard.putNumber("right joystick", Robot.rightStick.getYAxis());
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