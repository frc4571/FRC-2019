package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.command.TeleOpDrive;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

import static com.rambots4571.rampage.ctre.motor.TalonUtils.checkError;

public class Drivetrain extends Subsystem {
    private static Drivetrain instance;
    private TalonSRX leftMaster;
    private TalonSRX rightMaster;
    public final AHRS navx;

    private Drivetrain() {
        leftMaster = new TalonSRX(Constants.Drive.LEFT_MASTER);
        leftMaster.configFactoryDefault();
        leftMaster.configNeutralDeadband(Constants.Drive.deadband);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftMaster.setInverted(true);
        leftMaster.configOpenloopRamp(0.15, Constants.timeoutMs);

        rightMaster = new TalonSRX(Constants.Drive.RIGHT_MASTER);
        rightMaster.configFactoryDefault();
        rightMaster.configNeutralDeadband(Constants.Drive.deadband);
        rightMaster.setNeutralMode(NeutralMode.Coast);
        leftMaster.configOpenloopRamp(0.15, Constants.timeoutMs);

        VictorSPX leftFollower1 = new VictorSPX(Constants.Drive.LEFT_FOLLOWER1);
        leftFollower1.configFactoryDefault();
        leftFollower1.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower1.setNeutralMode(NeutralMode.Coast);
        leftFollower1.follow(leftMaster);
        leftFollower1.setInverted(InvertType.FollowMaster);
        leftFollower1.configOpenloopRamp(0.15, Constants.timeoutMs);

        VictorSPX leftFollower2 = new VictorSPX(Constants.Drive.LEFT_FOLLOWER2);
        leftFollower2.configFactoryDefault();
        leftFollower2.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower2.setNeutralMode(NeutralMode.Coast);
        leftFollower2.follow(leftMaster);
        leftFollower2.setInverted(InvertType.FollowMaster);
        leftFollower2.configOpenloopRamp(0.15, Constants.timeoutMs);

        VictorSPX rightFollower1 = new VictorSPX(Constants.Drive.RIGHT_FOLLOWER1);
        rightFollower1.configFactoryDefault();
        rightFollower1.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower1.setNeutralMode(NeutralMode.Coast);
        rightFollower1.follow(rightMaster);
        rightFollower1.setInverted(InvertType.FollowMaster);
        rightFollower1.configOpenloopRamp(0.15, Constants.timeoutMs);

        VictorSPX rightFollower2 = new VictorSPX(Constants.Drive.RIGHT_FOLLOWER2);
        rightFollower2.configFactoryDefault();
        rightFollower2.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower2.setNeutralMode(NeutralMode.Coast);
        rightFollower2.follow(rightMaster);
        rightFollower2.setInverted(InvertType.FollowMaster);
        rightFollower2.configOpenloopRamp(0.15, Constants.timeoutMs);

        navx = new AHRS(SPI.Port.kMXP);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleOpDrive());
    }

    public static Drivetrain getInstance() {
        if (instance == null) {
            synchronized (Drivetrain.class) {
                instance = new Drivetrain();
            }
        }
        return instance;
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
                Constants.Drive.MP.Gains.kP, Constants.Drive.MP.Gains.kI,
                Constants.Drive.MP.Gains.kD, Constants.Drive.MP.Gains.kF,
                Constants.timeoutMs);
        TalonUtils.config_PIDF(
                rightMaster, Constants.Drive.highGearPIDSlotIdx,
                Constants.Drive.MP.Gains.kP, Constants.Drive.MP.Gains.kI,
                Constants.Drive.MP.Gains.kD, Constants.Drive.MP.Gains.kF,
                Constants.timeoutMs);
    }

    public void drive(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    public void stop() {
        drive(0, 0);
    }
}