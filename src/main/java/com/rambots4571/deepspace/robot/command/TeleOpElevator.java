package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();
    private double maxAcceleration;
    private double maxVel;
    private double prevVel;
    private boolean prevButton;
    private boolean currentButton;

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
        maxAcceleration = 0;
        maxVel = 0;
        prevVel = 0;
        prevButton = false;
        currentButton = false;
    }

    private void log() {
        double vel = elevator.getVelocity(Constants.Units.Ticks);
        double acceleration = (vel - prevVel) / 0.02;
        prevVel = vel;
        if (Math.abs(acceleration) > Math.abs(maxAcceleration))
            maxAcceleration = Math.abs(acceleration);
        if (Math.abs(vel) > Math.abs(maxVel)) maxVel = Math.abs(vel);
        SmartDashboard.putNumber(
                "elevator encoder tick", elevator.getEncoderTick());
        SmartDashboard.putNumber("elevator height", elevator.getHeight());
        SmartDashboard.putNumber("velocity u/100ms", vel);
        SmartDashboard.putNumber("max velocity u/100ms", maxVel);
        SmartDashboard.putNumber("acceleration u/100ms^2", acceleration);
        SmartDashboard.putNumber("max acceleration u/100ms^2", maxAcceleration);
        SmartDashboard.putString(
                "position mode", elevator.getPositionMode().toString());
        System.out.println(elevator.getEncoderTick());
    }

    @Override
    protected void execute() {
        // toggling position mode
        prevButton = currentButton;
        currentButton = gamepad.getRightBumper().get();
        if (currentButton && !prevButton) elevator.togglePositionMode();
        // set position
        if (gamepad.getY().get())
            elevator.setPosition(Elevator.Height.Top);
        else if (gamepad.getB().get())
            elevator.setPosition(Elevator.Height.Middle);
        else if (gamepad.getA().get())
            elevator.setPosition(Elevator.Height.Bottom);
        else if (gamepad.getLeftBumper().get()) elevator.setPosition(0);
        else elevator.setBaseMotor(gamepad.getLeftYAxis());
        // small elevator manual control
        if (gamepad.getPOV() == 0) elevator.setTopMotor(1);
        else if (gamepad.getPOV() == 180) elevator.setTopMotor(-1);
        else elevator.setTopMotor(0);
        // resetting encoder once past zero
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
