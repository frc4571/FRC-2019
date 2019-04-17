package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private Elevator.Position position = elevator.getPosition();
    private ControlMode controlMode = ControlMode.Manual;

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
                    controlMode = ControlMode.MotionMagic;
                    position.setHeight(Elevator.Height.Top);
                }));
        gamepad.getB().whenPressed(new InstantCommand(
                () -> {
                    controlMode = ControlMode.MotionMagic;
                    position.setHeight(Elevator.Height.Middle);
                }));
        gamepad.getA().whenPressed(new InstantCommand(
                () -> {
                    controlMode = ControlMode.MotionMagic;
                    position.setHeight(Elevator.Height.Bottom);
                }));
        gamepad.getLeftBumper().whenPressed(new InstantCommand(
                () -> {
                    controlMode = ControlMode.MotionMagic;
                    position.setHeight(Elevator.Height.Zero);
                }));
    }

    @Override
    protected void execute() {
        double y = gamepad.getLeftYAxis();
        if (Math.abs(y) > 0.2) controlMode = ControlMode.Manual;

        if (controlMode == ControlMode.MotionMagic)
            elevator.gotoHeight(position.getHeight());
        else if (controlMode == ControlMode.Manual)
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

    private enum ControlMode {
        MotionMagic, Manual
    }
}
