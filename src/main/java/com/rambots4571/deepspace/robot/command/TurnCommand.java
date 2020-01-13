package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import static com.rambots4571.deepspace.robot.Constants.Drive.Turn.*;

public class TurnCommand extends PIDCommand {
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public TurnCommand(double angle) {
        super(new PIDController(kP, kI, kD),
              Drivetrain.getInstance()::getHeading,
              angle,
              output -> Drivetrain.getInstance().drive(-output, output),
        Drivetrain.getInstance());
        getController().enableContinuousInput(-180, 180);
        getController().setTolerance(2.0);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}