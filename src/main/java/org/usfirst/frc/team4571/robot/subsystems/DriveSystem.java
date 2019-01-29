package org.usfirst.frc.team4571.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.rampage.hardware.LazyTalonSRX;
import com.rambots4571.rampage.hardware.TalonSRXFactory;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;

public final class DriveSystem extends Subsystem {
    private LazyTalonSRX leftMaster;
    private LazyTalonSRX leftFollower;
    private LazyTalonSRX rightMaster;
    private LazyTalonSRX rightFollower;
    private AHRS navx;
    private PIDController turnController;

    public DriveSystem() {
        leftMaster = TalonSRXFactory.INSTANCE.createDefaultTalon(
                Constants.DRIVE.LEFT_MASTER);
        leftMaster.setInverted(true);
        rightMaster = TalonSRXFactory.INSTANCE.createDefaultTalon(
                Constants.DRIVE.RIGHT_MASTER);
        rightMaster.setInverted(true);
        leftFollower = TalonSRXFactory.INSTANCE.createFollowerTalon(
                Constants.DRIVE.LEFT_FOLLOWER,
                Constants.DRIVE.LEFT_MASTER);
        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower = TalonSRXFactory.INSTANCE.createFollowerTalon(
                Constants.DRIVE.RIGHT_FOLLOWER,
                Constants.DRIVE.RIGHT_MASTER);
        rightFollower.setInverted(InvertType.FollowMaster);

        leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs);
        rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs);

        rightMaster.setSensorPhase(true);

        navx = new AHRS(SPI.Port.kMXP);
        turnController = new PIDController(Constants.DRIVE.Turn.kP,
                                           Constants.DRIVE.Turn.kI,
                                           Constants.DRIVE.Turn.kD,
                                           navx, new TurnOutput());
        turnController.setInputRange(-180.0, 180.0);
        turnController.setOutputRange(-0.8, 0.8);
        turnController.setAbsoluteTolerance(3.0);
    }

    @Override
    protected void initDefaultCommand() {}

    public int getLeftEncoderTick() {
        return leftMaster.getSelectedSensorPosition(
                Constants.DRIVE.highGearPIDSlotIdx);
    }

    public int getRightEncoderTick() {
        return rightMaster.getSelectedSensorPosition(
                Constants.DRIVE.highGearPIDSlotIdx);
    }

    public double getLeftDistance(Constants.Unit unit) {
        return getLeftEncoderTick() / ((unit == Constants.Unit.Feet) ?
                                       Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                                       Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH);
    }

    public double getRightDistance(Constants.Unit unit) {
        return getRightEncoderTick() / ((unit == Constants.Unit.Feet) ?
                                        Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                                        Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH);
    }

    public double getLeftVelocity(Constants.Unit unit) {
        return leftMaster.getSelectedSensorVelocity(Constants.DRIVE.highGearPIDSlotIdx);
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public double getHeading() {
        return navx.getAngle();
    }

    public LazyTalonSRX[] getTalonMasters() {
        return new LazyTalonSRX[] {leftMaster, rightMaster};
    }

    public void resetGyro() {
        navx.reset();
    }

    public void turnToAngle(double angle) {
        turnController.reset();
        turnController.setSetpoint(angle);
        turnController.enable();
    }

    public PIDController getTurnController() {
        return turnController;
    }

    public void configMPGains() {
        leftMaster.configurePIDF(
                Constants.MPGains.kP,
                Constants.MPGains.kI,
                Constants.MPGains.kD,
                Constants.MPGains.kF,
                Constants.DRIVE.highGearPIDSlotIdx,
                Constants.timeoutMs);
        rightMaster.configurePIDF(
                Constants.MPGains.kP,
                Constants.MPGains.kI,
                Constants.MPGains.kD,
                Constants.MPGains.kF,
                Constants.DRIVE.highGearPIDSlotIdx,
                Constants.timeoutMs);
    }

    public void drive(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    public void stop() {
        drive(0, 0);
    }

    private class TurnOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            drive(-output, output);
        }
    }
}