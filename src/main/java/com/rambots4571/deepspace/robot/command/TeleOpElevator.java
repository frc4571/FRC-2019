package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        gamepad.getRightBumper().whileHeld(
                new InstantCommand(elevator::togglePositionMode));
        gamepad.getY().whileHeld(new InstantCommand(
                () -> elevator.gotoHeight(Elevator.Height.Top)));
        gamepad.getB().whileHeld(new InstantCommand(
                () -> elevator.gotoHeight(Elevator.Height.Middle)));
        gamepad.getA().whileHeld(new InstantCommand(
                () -> elevator.gotoHeight(Elevator.Height.Bottom)));
        gamepad.getLeftBumper().whileHeld(new InstantCommand(
                () -> elevator.gotoHeight(Elevator.Height.Zero)));
    }

    @Override
    protected void execute() {
        elevator.setBaseMotor(gamepad.getLeftYAxis());
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
