package org.usfirst.frc.team4571.robot.command.teleop;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystem.Intake;

public class TeleOpIntake extends Command {
    private Intake intake = Intake.getInstance();
    private JoystickButton intakeButton = Robot.leftStick.getButton1();
    private JoystickButton outtakeButton = Robot.rightStick.getButton1();

    public TeleOpIntake() {
        requires(intake);
    }

    @Override
    protected void execute() {
        if (intakeButton.get() ^ outtakeButton.get()) {
            if (intakeButton.get()) {
                intake.setIntakePower(1);
            } else if (outtakeButton.get()) {
                intake.setIntakePower(-1);
            }
        } else {
            intake.setIntakePower(0);
        }
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
