package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.rampage.ctre.motor.TalonUtilsKt;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpDrive;

import static com.rambots4571.rampage.ctre.motor.TalonUtilsKt.checkError;

public final class DriveSystem extends Subsystem {
    private static DriveSystem instance;
    private TalonSRX leftMaster;
    private TalonSRX rightMaster;
    private AHRS navx;
    private PIDController turnController;

    private DriveSystem() {
        leftMaster = new TalonSRX(Constants.DRIVE.LEFT_MASTER);
        leftMaster.configFactoryDefault();
        leftMaster.setNeutralMode(NeutralMode.Brake);
        rightMaster = new TalonSRX(Constants.DRIVE.RIGHT_MASTER);
        rightMaster.configFactoryDefault();
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightMaster.setInverted(true);

        TalonSRX leftFollower1 = new TalonSRX(Constants.DRIVE.LEFT_FOLLOWER1);
        leftFollower1.configFactoryDefault();
        leftFollower1.setNeutralMode(NeutralMode.Brake);
        leftFollower1.follow(leftMaster);
        leftFollower1.setInverted(InvertType.FollowMaster);

        TalonSRX leftFollower2 = new TalonSRX(Constants.DRIVE.LEFT_FOLLOWER2);
        leftFollower2.configFactoryDefault();
        leftFollower2.setNeutralMode(NeutralMode.Brake);
        leftFollower2.follow(leftMaster);
        leftFollower2.setInverted(InvertType.FollowMaster);

        TalonSRX rightFollower1 = new TalonSRX(Constants.DRIVE.RIGHT_FOLLOWER1);
        rightFollower1.configFactoryDefault();
        rightFollower1.setNeutralMode(NeutralMode.Brake);
        rightFollower1.follow(rightMaster);
        rightFollower1.setInverted(InvertType.FollowMaster);

        TalonSRX rightFollower2 = new TalonSRX(Constants.DRIVE.RIGHT_FOLLOWER2);
        rightFollower2.configFactoryDefault();
        rightFollower2.setNeutralMode(NeutralMode.Brake);
        rightFollower2.follow(rightMaster);
        rightFollower2.setInverted(InvertType.FollowMaster);

        checkError(leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs), "can't initialize left encoder!");

        checkError(rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0,
                Constants.timeoutMs), "can't initialize right encoder!");

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
    protected void initDefaultCommand() {
        setDefaultCommand(TeleOpDrive.getInstance());
    }

    public static DriveSystem getInstance() {
        if (instance == null) {
            instance = new DriveSystem();
        }
        return instance;
    }

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
        return leftMaster.getSelectedSensorVelocity(
                Constants.DRIVE.highGearPIDSlotIdx) /
               ((unit == Constants.Unit.Feet) ?
                Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH) / 10.0;
    }

    public double getRightVelocity(Constants.Unit unit) {
        return rightMaster.getSelectedSensorVelocity(
                Constants.DRIVE.highGearPIDSlotIdx) /
               ((unit == Constants.Unit.Feet) ?
                Constants.Transmission.HIGH_GEAR_TICKS_PER_FEET :
                Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH) / 10.0;
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

    public void turnToAngle(double angle) {
        turnController.reset();
        turnController.setSetpoint(angle);
        turnController.enable();
    }

    public PIDController getTurnController() {
        return turnController;
    }

    public void configMPGains() {
        TalonUtilsKt.config_PIDF(
                leftMaster, Constants.DRIVE.highGearPIDSlotIdx,
                Constants.MPGains.kP, Constants.MPGains.kI,
                Constants.MPGains.kD, Constants.MPGains.kF,
                Constants.timeoutMs);
        TalonUtilsKt.config_PIDF(
                rightMaster, Constants.DRIVE.highGearPIDSlotIdx,
                Constants.MPGains.kP, Constants.MPGains.kI,
                Constants.MPGains.kD, Constants.MPGains.kF,
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