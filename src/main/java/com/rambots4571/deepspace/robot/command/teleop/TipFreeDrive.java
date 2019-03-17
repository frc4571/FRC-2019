package com.rambots4571.deepspace.robot.command.teleop;

import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.deepspace.robot.subsystem.Elevator;
import com.rambots4571.rampage.sensor.pid.SourceSupplier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TipFreeDrive extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Elevator elevator = Elevator.getInstance();
    private PIDController tipController;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double tipCompensation;

    public TipFreeDrive() {
        requires(drivetrain);
        requires(elevator);
        tipController = new PIDController(
                kP, kI, kD, new SourceSupplier(
                () -> (double) drivetrain.navx.getRoll()),
                output -> tipCompensation = output);
        tipController.setInputRange(-180, 180);
        tipController.setOutputRange(-0.5, 0.5);
        tipController.setAbsoluteTolerance(5);
    }

    @Override
    protected void initialize() {
        drivetrain.resetGyro();
        tipController.reset();
        tipController.setSetpoint(0);
        SmartDashboard.putData(tipController);
        tipController.enable();
    }

    @Override
    protected void execute() {
        if (Math.abs(drivetrain.navx.getRoll()) > 5) drivetrain.drive(
                tipCompensation, tipCompensation);
        else drivetrain.drive(
                Robot.leftStick.getYAxis(), Robot.rightStick.getYAxis());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
