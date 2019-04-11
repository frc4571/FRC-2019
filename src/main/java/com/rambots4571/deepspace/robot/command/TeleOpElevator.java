package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private Elevator.Height height;
    private ControlType controlType;

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        height = elevator.getPosition().getHeight();
        controlType = ControlType.MotionMagic;
    }

    @Override
    protected void execute() {
        // toggling position mode
        gamepad.getRightBumper()
               .whenPressedDoOnce(elevator::togglePositionMode);
        gamepad.getLeftStick().whenPressedDoOnce(
                () -> controlType = controlType == ControlType.MotionMagic ?
                                    ControlType.Manual :
                                    ControlType.MotionMagic);
        // set position
        if (controlType == ControlType.MotionMagic) {
            if (gamepad.getY().get()) height = Elevator.Height.Top;
            else if (gamepad.getB().get()) height = Elevator.Height.Middle;
            else if (gamepad.getA().get()) height = Elevator.Height.Bottom;
            else if (gamepad.getLeftBumper().get())
                height = Elevator.Height.Zero;
            elevator.setPosition(height);
        } else if (controlType == ControlType.Manual) {
            elevator.setBaseMotor(gamepad.getLeftYAxis());
        }
        // small elevator manual control
        if (gamepad.getPOV() == 0) elevator.setTopMotor(1);
        else if (gamepad.getPOV() == 180) elevator.setTopMotor(-1);
        else elevator.setTopMotor(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        elevator.stopBaseMotor();
        elevator.stopTopMotor();
    }

    private enum ControlType {
        MotionMagic, Manual
    }
}
