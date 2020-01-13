package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.rambots4571.deepspace.robot.Constants.Drive.MP.Gains.*;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance;
    public final AHRS navx;
    private TalonSRX leftMaster;
    private TalonSRX rightMaster;
    private NeutralMode neutralMode = NeutralMode.Coast;
    private double openLoopRampRate = 0.15;
    private int peakCurrent = 40;

    private Drivetrain() {
        leftMaster = new TalonSRX(Constants.Drive.LEFT_MASTER);
        leftMaster.configFactoryDefault();
        leftMaster.configNeutralDeadband(Constants.Drive.deadband);
        leftMaster.setNeutralMode(neutralMode);
        leftMaster.setInverted(true);
        leftMaster.configOpenloopRamp(openLoopRampRate, Constants.timeoutMs);
        leftMaster.enableCurrentLimit(true);
        leftMaster.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        leftMaster.configPeakCurrentLimit(peakCurrent, Constants.timeoutMs);
        leftMaster.configPeakCurrentDuration(0, Constants.timeoutMs);

        rightMaster = new TalonSRX(Constants.Drive.RIGHT_MASTER);
        rightMaster.configFactoryDefault();
        rightMaster.configNeutralDeadband(Constants.Drive.deadband);
        rightMaster.setNeutralMode(neutralMode);
        leftMaster.configOpenloopRamp(openLoopRampRate, Constants.timeoutMs);
        rightMaster.enableCurrentLimit(true);
        rightMaster.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        rightMaster.configPeakCurrentLimit(peakCurrent, Constants.timeoutMs);
        rightMaster.configPeakCurrentDuration(0, Constants.timeoutMs);


        TalonSRX leftFollower1 = new TalonSRX(Constants.Drive.LEFT_FOLLOWER1);
        leftFollower1.configFactoryDefault();
        leftFollower1.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower1.setNeutralMode(neutralMode);
        leftFollower1.follow(leftMaster);
        leftFollower1.setInverted(InvertType.FollowMaster);
        leftFollower1.configOpenloopRamp(openLoopRampRate, Constants.timeoutMs);
        leftFollower1.enableCurrentLimit(true);
        leftFollower1.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        leftFollower1.configPeakCurrentLimit(peakCurrent);
        leftFollower1.configPeakCurrentDuration(0, Constants.timeoutMs);


        TalonSRX leftFollower2 = new TalonSRX(Constants.Drive.LEFT_FOLLOWER2);
        leftFollower2.configFactoryDefault();
        leftFollower2.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower2.setNeutralMode(neutralMode);
        leftFollower2.follow(leftMaster);
        leftFollower2.setInverted(InvertType.FollowMaster);
        leftFollower2.configOpenloopRamp(openLoopRampRate, Constants.timeoutMs);
        leftFollower2.enableCurrentLimit(true);
        leftFollower2.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        leftFollower2.configPeakCurrentLimit(peakCurrent);
        leftFollower2.configPeakCurrentDuration(0, Constants.timeoutMs);


        TalonSRX rightFollower1 = new TalonSRX(Constants.Drive.RIGHT_FOLLOWER1);
        rightFollower1.configFactoryDefault();
        rightFollower1.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower1.setNeutralMode(neutralMode);
        rightFollower1.follow(rightMaster);
        rightFollower1.setInverted(InvertType.FollowMaster);
        rightFollower1.configOpenloopRamp(
                openLoopRampRate, Constants.timeoutMs);
        rightFollower1.enableCurrentLimit(true);
        rightFollower1.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        rightFollower1.configPeakCurrentLimit(peakCurrent);
        rightFollower1.configPeakCurrentDuration(0, Constants.timeoutMs);


        TalonSRX rightFollower2 = new TalonSRX(Constants.Drive.RIGHT_FOLLOWER2);
        rightFollower2.configFactoryDefault();
        rightFollower2.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower2.setNeutralMode(neutralMode);
        rightFollower2.follow(rightMaster);
        rightFollower2.setInverted(InvertType.FollowMaster);
        rightFollower2.configOpenloopRamp(
                openLoopRampRate, Constants.timeoutMs);
        rightFollower2.enableCurrentLimit(true);
        rightFollower2.configContinuousCurrentLimit(
                peakCurrent, Constants.timeoutMs);
        rightFollower2.configPeakCurrentLimit(peakCurrent);
        rightFollower2.configPeakCurrentDuration(0, Constants.timeoutMs);


        navx = new AHRS(SPI.Port.kMXP);
    }

    public static Drivetrain getInstance() {
        if (instance == null) {
            synchronized (Drivetrain.class) {
                instance = new Drivetrain();
            }
        }
        return instance;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Gyro Heading", this::getHeading, null);
        builder.addDoubleProperty(
                "Ramp Rate", () -> openLoopRampRate,
                value -> openLoopRampRate = value);
        builder.addStringProperty(
                "Neutral Mode", () -> neutralMode.toString(), null);
    }

    public int getLeftEncoderTick() {
        return leftMaster.getSelectedSensorPosition(
                Constants.Drive.highGearPIDSlotIdx);
    }

    public int getRightEncoderTick() {
        return rightMaster.getSelectedSensorPosition(
                Constants.Drive.highGearPIDSlotIdx);
    }

    public double getLeftDistance(Constants.Units units) {
        return getLeftEncoderTick() /
               ((units == Constants.Units.Feet) ?
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_INCH);
    }

    public double getRightDistance(Constants.Units units) {
        return getRightEncoderTick() /
               ((units == Constants.Units.Feet) ?
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_INCH);
    }

    public double getLeftVelocity(Constants.Units units) {
        return leftMaster.getSelectedSensorVelocity(
                Constants.Drive.highGearPIDSlotIdx) /
               ((units == Constants.Units.Feet) ?
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_INCH) / 10.0;
    }

    public double getRightVelocity(Constants.Units units) {
        return rightMaster.getSelectedSensorVelocity(
                Constants.Drive.highGearPIDSlotIdx) /
               ((units == Constants.Units.Feet) ?
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Drive.Transmission.HIGH_GEAR_TICKS_PER_INCH) / 10.0;
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public double getHeading() {
        return navx.getAngle();
    }

    public TalonSRX[] getTalonMasters() {
        return new TalonSRX[] {leftMaster, rightMaster};
    }

    public void resetGyro() {
        navx.reset();
    }

    public void configMPGains() {
        TalonUtils.config_PIDF(
                leftMaster, Constants.Drive.highGearPIDSlotIdx,
                kP, kI, kD, kF, Constants.timeoutMs);
        TalonUtils.config_PIDF(
                rightMaster, Constants.Drive.highGearPIDSlotIdx,
                kP, kI, kD, kF, Constants.timeoutMs);
    }

    public void drive(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    public void stop() {
        drive(0, 0);
    }
}