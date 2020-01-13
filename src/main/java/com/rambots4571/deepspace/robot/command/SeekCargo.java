package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.deepspace.robot.subsystem.Intake;
import com.rambots4571.rampage.vision.Limelight;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SeekCargo extends CommandBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Intake intake = Intake.getInstance();
    private PIDController turnController;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    public SeekCargo() {
        addRequirements(intake, drivetrain);
        turnController = new PIDController(kP, kI, kD);
    }

    @Override
    public void initialize() {
        turnController.reset();
        turnController.setSetpoint(0);
    }

    @Override
    public void execute() {
        double turnOutput = turnController.calculate(Limelight.getXOffset());
        intake.setIntakePower(0.5);
        drivetrain.drive(0.4 - turnOutput, 0.4 + turnOutput);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
        intake.setIntakePower(0);
    }
}
