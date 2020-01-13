package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleOpIntake extends CommandBase {
    private Intake intake = Intake.getInstance();

    public TeleOpIntake() {
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setIntakePower(Robot.gamepad.getLeftTrigger() -
                              Robot.gamepad.getRightTrigger());
        intake.setPulleyPower(Robot.gamepad.getRightYAxis() * 0.5);
        if (Robot.gamepad.getBackButton().get()) intake.setHatchMotor(0.35);
        else if (Robot.gamepad.getStartButton().get()) intake.setHatchMotor(
                -0.35);
        else intake.setHatchMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntakePower(0);
        intake.setPulleyPower(0);
    }
}
