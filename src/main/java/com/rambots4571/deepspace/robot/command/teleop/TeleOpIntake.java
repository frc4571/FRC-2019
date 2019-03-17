package com.rambots4571.deepspace.robot.command.teleop;

import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Intake;
import edu.wpi.first.wpilibj.command.Command;

public class TeleOpIntake extends Command {
    private Intake intake = Intake.getInstance();

    public TeleOpIntake() {
        requires(intake);
    }

    @Override
    protected void execute() {
        intake.setIntakePower(Robot.gamepad.getLeftTrigger() -
                              Robot.gamepad.getRightTrigger());
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
