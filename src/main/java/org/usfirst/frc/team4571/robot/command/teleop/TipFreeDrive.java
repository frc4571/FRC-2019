package org.usfirst.frc.team4571.robot.command.teleop;

import com.rambots4571.rampage.sensor.pid.SourceSupplier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystem.Drivetrain;

public class TipFreeDrive extends Command {
    Drivetrain drivetrain = Drivetrain.getInstance();
    PIDController tipController;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double tipCompentation;

    public TipFreeDrive() {
        requires(drivetrain);
        tipController = new PIDController(
                kP, kI, kD, new SourceSupplier(
                () -> (double) drivetrain.navx.getRoll()),
                output -> tipCompentation = output);
        tipController.setInputRange(-180, 180);
        tipController.setOutputRange(-0.5, 0.5);
        tipController.setAbsoluteTolerance(5);
    }

    @Override
    protected void initialize() {
        tipController.reset();
        tipController.setSetpoint(0);
        SmartDashboard.putData(tipController);
        tipController.enable();
    }

    @Override
    protected void execute() {
        if (Math.abs(drivetrain.navx.getRoll()) > 5) drivetrain.drive(
                tipCompentation, tipCompentation);
        else drivetrain.drive(
                Robot.leftStick.getYAxis(), Robot.rightStick.getYAxis());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
