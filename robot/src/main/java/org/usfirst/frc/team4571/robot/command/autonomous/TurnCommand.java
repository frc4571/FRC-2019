package org.usfirst.frc.team4571.robot.command.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.subsystem.DriveSystem;

public class TurnCommand extends Command {
    private DriveSystem drivetrain = DriveSystem.getInstance();
    private double angle;

    public TurnCommand(double angle) {
        this.angle = angle;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        drivetrain.resetGyro();
        drivetrain.turnToAngle(angle);
        SmartDashboard.putData(drivetrain.getTurnController());
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Heading", drivetrain.getHeading());
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.getTurnController().onTarget();
    }

    @Override
    protected void end() {
        drivetrain.getTurnController().disable();
        drivetrain.stop();
    }
}