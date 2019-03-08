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
        intake.setIntakePower(Robot.gamepad.getLeftTrigger() - Robot.gamepad.getRightTrigger());
        intake.setPulleyPower(Robot.gamepad.getRightYAxis());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        intake.setIntakePower(0);
        intake.setPulleyPower(0);
    }
}
