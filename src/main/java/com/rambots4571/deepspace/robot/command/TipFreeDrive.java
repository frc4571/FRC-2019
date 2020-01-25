package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.RobotContainer;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TipFreeDrive extends CommandBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private final double kP = 0.0;
    private final double kI = 0.0;
    private final double kD = 0.0;
    private PIDController tipController;

    public TipFreeDrive() {
        addRequirements(drivetrain);
        tipController = new PIDController(kP, kI, kD);
    }

    @Override
    public void initialize() {
        tipController.reset();
        tipController.setSetpoint(0);
    }

    @Override
    public void execute() {
        double output = tipController.calculate(drivetrain.navx.getRoll());
        if (drivetrain.navx.getRoll() > 5f) drivetrain.drive(output, output);
        else drivetrain.drive(
                RobotContainer.leftStick.getYAxis(),
                RobotContainer.rightStick.getYAxis());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(0, 0);
    }
}
