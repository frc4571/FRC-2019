package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpDrive;

import static com.rambots4571.rampage.ctre.motor.TalonUtils.checkError;

public class Drivetrain extends Subsystem {
    private static Drivetrain instance;
    private TalonSRX leftMaster;
    private TalonSRX rightMaster;
    private AHRS navx;

    private Drivetrain() {
        leftMaster = new TalonSRX(Constants.Drive.LEFT_MASTER);
        leftMaster.configFactoryDefault();
        leftMaster.configNeutralDeadband(Constants.Drive.deadband);
        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftMaster.setInverted(true);
        leftMaster.configOpenloopRamp(0.15, Constants.timeoutMs);

        rightMaster = new TalonSRX(Constants.Drive.RIGHT_MASTER);
        rightMaster.configFactoryDefault();
        rightMaster.setSensorPhase(true);
        rightMaster.configNeutralDeadband(Constants.Drive.deadband);
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightMaster.configOpenloopRamp(0.15, Constants.timeoutMs);

        TalonSRX leftFollower1 = new TalonSRX(Constants.Drive.LEFT_FOLLOWER1);
        leftFollower1.configFactoryDefault();
        leftFollower1.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower1.setNeutralMode(NeutralMode.Brake);
        leftFollower1.follow(leftMaster);
        leftFollower1.setInverted(InvertType.FollowMaster);
        leftFollower1.configOpenloopRamp(0.15, Constants.timeoutMs);

        TalonSRX leftFollower2 = new TalonSRX(Constants.Drive.LEFT_FOLLOWER2);
        leftFollower2.configFactoryDefault();
        leftFollower2.configNeutralDeadband(Constants.Drive.deadband);
        leftFollower2.setNeutralMode(NeutralMode.Brake);
        leftFollower2.follow(leftMaster);
        leftFollower2.setInverted(InvertType.FollowMaster);
        leftFollower2.configOpenloopRamp(0.15, Constants.timeoutMs);

        TalonSRX rightFollower1 = new TalonSRX(
                Constants.Drive.RIGHT_FOLLOWER1);
        rightFollower1.configFactoryDefault();
        rightFollower1.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower1.setNeutralMode(NeutralMode.Brake);
        rightFollower1.follow(rightMaster);
        rightFollower1.setInverted(InvertType.FollowMaster);
        rightFollower1.configOpenloopRamp(0.15, Constants.timeoutMs);

        TalonSRX rightFollower2 = new TalonSRX(
                Constants.Drive.RIGHT_FOLLOWER2);
        rightFollower2.configFactoryDefault();
        rightFollower2.configNeutralDeadband(Constants.Drive.deadband);
        rightFollower2.setNeutralMode(NeutralMode.Brake);
        rightFollower2.follow(rightMaster);
        rightFollower2.setInverted(InvertType.FollowMaster);
        rightFollower2.configOpenloopRamp(0.15, Constants.timeoutMs);

        checkError(leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs), "can't initialize left encoder!");

        checkError(rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs), "can't initialize right encoder!");

        navx = new AHRS(SPI.Port.kMXP);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleOpDrive());
    }

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
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

    public AHRS getNavx() {
        return navx;
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