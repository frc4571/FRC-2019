package com.rambots4571.deepspace.robot.command;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.rambots4571.deepspace.robot.RobotContainer;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleOpDrive extends CommandBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public TeleOpDrive() {
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber(
                "left encoder raw", drivetrain.getLeftEncoderTick());
        SmartDashboard.putNumber(
                "right encoder raw", drivetrain.getRightEncoderTick());
        SmartDashboard.putNumber(
                "left distance (ft)", drivetrain.getLeftDistance());
        SmartDashboard.putNumber(
                "right distance (ft)", drivetrain.getRightDistance());
        drivetrain.drive(
                RobotContainer.leftStick.getYAxis(),
                RobotContainer.rightStick.getYAxis());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
        drivetrain.resetEncoders();
    }
}