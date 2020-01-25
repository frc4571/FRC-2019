package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.RobotContainer;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleOpDrive extends CommandBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public TeleOpDrive() {
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.drive(
                RobotContainer.leftStick.getYAxis(), RobotContainer.rightStick.getYAxis());
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