package org.usfirst.frc.team4571.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Robot;

public class TurnCommand extends Command {
    private double angle;

    public TurnCommand(double angle) {
        this.angle = angle;
        requires(Robot.DRIVE_SYSTEM);
    }

    @Override
    protected void initialize() {
        Robot.DRIVE_SYSTEM.resetGyro();
        Robot.DRIVE_SYSTEM.turnToAngle(angle);
        SmartDashboard.putData(Robot.DRIVE_SYSTEM.getTurnController());
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Heading", Robot.DRIVE_SYSTEM.getHeading());
    }

    @Override
    protected boolean isFinished() {
        return Robot.DRIVE_SYSTEM.getTurnController().onTarget();
    }

    @Override
    protected void end() {
        Robot.DRIVE_SYSTEM.getTurnController().disable();
        Robot.DRIVE_SYSTEM.stop();
    }
}