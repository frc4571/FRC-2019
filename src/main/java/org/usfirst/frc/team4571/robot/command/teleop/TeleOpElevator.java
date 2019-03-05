package org.usfirst.frc.team4571.robot.command.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.Robot;
import org.usfirst.frc.team4571.robot.subsystem.Elevator;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
    }

    private void log() {
        SmartDashboard.putNumber(
                "elevator encoder tick",
                elevator.getEncoderTick());
        SmartDashboard.putNumber("elevator height", elevator.getHeight());
        SmartDashboard.putNumber("velocity", elevator.getVelocity(
                Constants.Units.Ticks));
    }

    @Override
    protected void execute() {
        elevator.setBaseMotor(Robot.gamepad.getLeftYAxis());
        log();
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
