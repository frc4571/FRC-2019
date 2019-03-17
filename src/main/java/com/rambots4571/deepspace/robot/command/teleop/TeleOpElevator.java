package com.rambots4571.deepspace.robot.command.teleop;

import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private double maxAcceleration;
    private double maxVel;
    private double prevVel;

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        elevator.resetEncoder();
        maxAcceleration = 0;
        maxVel = 0;
        prevVel = 0;
    }

    private void log() {
        double vel = elevator.getVelocity(Constants.Units.Inches);
        double acceleration = (vel - prevVel) / 0.02;
        prevVel = vel;
        if (Math.abs(acceleration) > Math.abs(maxAcceleration))
            maxAcceleration = Math.abs(acceleration);
        if (Math.abs(vel) > Math.abs(maxVel)) maxVel = Math.abs(vel);
        SmartDashboard.putNumber(
                "elevator encoder tick", elevator.getEncoderTick());
        SmartDashboard.putNumber("elevator height", elevator.getHeight());
        SmartDashboard.putNumber("velocity in/s", vel);
        SmartDashboard.putNumber("max velocity in/s", maxVel);
        SmartDashboard.putNumber("acceleration in/s^2", acceleration);
        SmartDashboard.putNumber("max acceleration in/s^2", maxAcceleration);
    }

    @Override
    protected void execute() {
        elevator.setBaseMotor(Robot.gamepad.getLeftYAxis());
        if (Robot.gamepad.getPOV() == 0) elevator.setTopMotor(0.5);
        else if (Robot.gamepad.getPOV() == 180) elevator.setTopMotor(-0.5);
        else elevator.setTopMotor(0);
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
