package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private Elevator.Height height = elevator.getPosition().getHeight();
    private ControlType controlType = ControlType.Manual;

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        gamepad.getRightBumper().whenPressed(
                new InstantCommand(elevator::togglePositionMode));
        gamepad.getY().whenPressed(new InstantCommand(
                () -> {
                    controlType = ControlType.MotionMagic;
                    height = Elevator.Height.Top;
                }));
        gamepad.getX().whenPressed(new InstantCommand(
                () -> {
                    controlType = ControlType.MotionMagic;
                    height = Elevator.Height.CargoShip;
                }));
        gamepad.getB().whenPressed(new InstantCommand(
                () -> {
                    controlType = ControlType.MotionMagic;
                    height = Elevator.Height.Middle;
                }));
        gamepad.getA().whenPressed(new InstantCommand(
                () -> {
                    controlType = ControlType.MotionMagic;
                    height = Elevator.Height.Bottom;
                }));
        gamepad.getLeftBumper().whenPressed(new InstantCommand(
                () -> {
                    controlType = ControlType.MotionMagic;
                    height = Elevator.Height.Zero;
                }));
    }

    @Override
    protected void execute() {
        double y = gamepad.getLeftYAxis();
        if (Math.abs(y) > 0.2) controlType = ControlType.Manual;

        if (controlType == ControlType.MotionMagic)
            elevator.gotoHeight(height);
        else if (controlType == ControlType.Manual)
            elevator.setBaseMotor(y);

        if (elevator.isLimitSwitchPressed()) elevator.resetEncoder();
        // small elevator manual control
        int pov = gamepad.getPOV();
        if (pov == 0) elevator.setTopMotor(1);
        else if (pov == 180) elevator.setTopMotor(-1);
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
