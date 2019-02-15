package org.usfirst.frc.team4571.robot.command.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystem.Elevator;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void execute() {
        if (elevator.isLimitSwitchPressed() && Robot.leftStick.getYAxis() < 0) {
            elevator.stopBaseMotor();
        } else {
            elevator.setBaseMotor(Robot.leftStick.getYAxis());
        }
        elevator.setTopMotor(Robot.rightStick.getYAxis());
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
