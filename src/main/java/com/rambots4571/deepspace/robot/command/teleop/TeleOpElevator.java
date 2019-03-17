package com.rambots4571.deepspace.robot.command.teleop;

import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.Robot;
import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private double maxAcceleration = 0;
    private double maxVel = 0;
    private double prevVel;

    public TeleOpElevator() {
        requires(elevator);
        prevVel = 0;
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        elevator.resetEncoder();
    }

    private void log() {
        double vel = elevator.getVelocity(Constants.Units.Inches);
        double acceleration = (vel - prevVel) / 0.02;
        prevVel = vel;
        if (Math.abs(acceleration) > Math.abs(maxAcceleration))
            maxAcceleration = acceleration;
        if (Math.abs(vel) > Math.abs(maxVel)) maxVel = vel;
        SmartDashboard.putNumber(
                "elevator encoder tick", elevator.getEncoderTick());
        SmartDashboard.putNumber("elevator height", elevator.getHeight());
        SmartDashboard.putNumber("velocity", vel);
        SmartDashboard.putNumber("max velocity", maxVel);
        SmartDashboard.putNumber("acceleration", acceleration);
        SmartDashboard.putNumber("max acceleration", maxAcceleration);
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
