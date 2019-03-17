package com.rambots4571.deepspace.robot.command.autonomous;

import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.rambots4571.deepspace.robot.Constants.Drive.Turn.*;

public class TurnCommand extends Command {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private PIDController turnController;
    private double angle;

    public TurnCommand(double angle) {
        this.angle = angle;
        requires(drivetrain);
        turnController = new PIDController(
                kP, kI, kD, drivetrain.navx,
                output -> drivetrain.drive(-output, output));
        turnController.setInputRange(-180.0, 180.0);
        turnController.setOutputRange(-0.8, 0.8);
        turnController.setAbsoluteTolerance(3.0);
    }

    @Override
    protected void initialize() {
        drivetrain.resetGyro();
        turnController.reset();
        turnController.setSetpoint(angle);
        SmartDashboard.putData(turnController);
        turnController.enable();
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Heading", drivetrain.getHeading());
    }

    @Override
    protected boolean isFinished() {
        return turnController.onTarget();
    }

    @Override
    protected void end() {
        turnController.disable();
        drivetrain.stop();
    }
}