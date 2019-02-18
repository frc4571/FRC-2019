package org.usfirst.frc.team4571.robot.command.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystem.Intake;

public class TeleOpIntake extends Command {
    private Intake intake = Intake.getInstance();

    public TeleOpIntake() {
        requires(intake);
    }

    @Override
    protected void execute() {
        if (Robot.leftStick.getButton1().get()) {
            intake.setPower(1.0);
        } else if (Robot.rightStick.getButton1().get()) {
            intake.setPower(-1.0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
