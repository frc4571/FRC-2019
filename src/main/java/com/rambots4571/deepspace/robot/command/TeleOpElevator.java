package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private boolean prevButton;
    private boolean currentButton;

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        prevButton = false;
        currentButton = false;
    }

    @Override
    protected void execute() {
        // toggling position mode
        gamepad.getRightBumper().whenPressedDoOnce(
                elevator::togglePositionMode);
        // set position
        if (gamepad.getX().get()) elevator.setPosition(Elevator.Height.Cargo);
        else if (gamepad.getY().get())
            elevator.setPosition(Elevator.Height.Top);
        else if (gamepad.getB().get())
            elevator.setPosition(Elevator.Height.Middle);
        else if (gamepad.getA().get())
            elevator.setPosition(Elevator.Height.Bottom);
        else if (gamepad.getLeftBumper().get())
            elevator.setPosition(Elevator.Height.Zero);
        else elevator.setBaseMotor(gamepad.getLeftYAxis());

        if (elevator.isLimitSwitchPressed()) elevator.resetEncoder();
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
}
