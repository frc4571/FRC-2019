package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.command.TeleOpElevator;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX baseMotorMaster, baseMotorFollower;
    private TalonSRX topMotor;
    private DigitalInput limitSwitch;
    private PositionMode positionMode;

    private Elevator() {
        baseMotorMaster = new TalonSRX(Constants.Elevator.BASE_MOTOR_MASTER);
        baseMotorMaster.configFactoryDefault();
        baseMotorMaster.setNeutralMode(NeutralMode.Brake);
        baseMotorMaster.configReverseLimitSwitchSource(
                LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen, Constants.timeoutMs);
        baseMotorMaster.enableCurrentLimit(true);
        baseMotorMaster.configContinuousCurrentLimit(25, Constants.timeoutMs);
        baseMotorMaster.configPeakCurrentLimit(30, Constants.timeoutMs);
        baseMotorMaster.configPeakCurrentDuration(500, Constants.timeoutMs);
        baseMotorMaster.configNeutralDeadband(0.06, Constants.timeoutMs);
        baseMotorMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.Elevator.kPIDLoopIdx, Constants.timeoutMs);
        baseMotorMaster.setStatusFramePeriod(
                StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
                Constants.timeoutMs);
        baseMotorMaster.setStatusFramePeriod(
                StatusFrameEnhanced.Status_10_MotionMagic, 10,
                Constants.timeoutMs);
        baseMotorMaster.configNominalOutputForward(0, Constants.timeoutMs);
        baseMotorMaster.configNominalOutputReverse(0, Constants.timeoutMs);
        baseMotorMaster.configPeakOutputForward(1, Constants.timeoutMs);
        baseMotorMaster.configPeakOutputReverse(-1, Constants.timeoutMs);
        baseMotorMaster.selectProfileSlot(
                Constants.Elevator.kSlotIdx, Constants.Elevator.kPIDLoopIdx);
        TalonUtils.config_PIDF(
                baseMotorMaster, Constants.Elevator.kPIDLoopIdx,
                Constants.Elevator.Gains.kP, Constants.Elevator.Gains.kI,
                Constants.Elevator.Gains.kD, Constants.Elevator.Gains.kF,
                Constants.timeoutMs);
        baseMotorMaster.configMotionCruiseVelocity(
                Constants.Elevator.cruiseVel, Constants.timeoutMs);
        baseMotorMaster.configMotionAcceleration(
                Constants.Elevator.acceleration, Constants.timeoutMs);
        baseMotorMaster.configOpenloopRamp(0.35, Constants.timeoutMs);

        baseMotorFollower = new TalonSRX(
                Constants.Elevator.BASE_MOTOR_FOLLOWER);
        baseMotorFollower.configFactoryDefault();
        baseMotorFollower.setNeutralMode(NeutralMode.Brake);
        baseMotorFollower.follow(baseMotorMaster);
        baseMotorFollower.setInverted(InvertType.FollowMaster);
        baseMotorFollower.enableCurrentLimit(true);
        baseMotorFollower.configContinuousCurrentLimit(25, Constants.timeoutMs);
        baseMotorFollower.configPeakCurrentLimit(30, Constants.timeoutMs);
        baseMotorFollower.configPeakCurrentDuration(500, Constants.timeoutMs);
        baseMotorFollower.configOpenloopRamp(0.35, Constants.timeoutMs);

        topMotor = new TalonSRX(Constants.Elevator.TOP_MOTOR);
        topMotor.configFactoryDefault();
        topMotor.setNeutralMode(NeutralMode.Brake);
        topMotor.setInverted(true);

        limitSwitch = new DigitalInput(Constants.Elevator.LIMIT_SWITCH);
        resetEncoder();
    }

    public static Elevator getInstance() {
        if (instance == null) {
            synchronized (Elevator.class) {
                instance = new Elevator();
            }
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleOpElevator());
    }

    @Override
    public void periodic() {
        SmartDashboard.getBoolean("limit switch pressed", isAtReverseLimit());
    }

    public void teleOpInit() {
        positionMode = PositionMode.Hatch;
    }

    public void setBaseMotor(double value) {
        baseMotorMaster.set(ControlMode.PercentOutput, value);
    }

    public void setTopMotor(double value) {
        topMotor.set(ControlMode.PercentOutput, value);
    }

    public void stopBaseMotor() {
        baseMotorMaster.set(ControlMode.PercentOutput, 0);
    }

    public void stopTopMotor() {
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLimitSwitchPressed() {
        return limitSwitch.get();
    }

    public void resetEncoder() {
        baseMotorMaster.setSelectedSensorPosition(0);
    }

    public int getEncoderTick() {
        return baseMotorMaster.getSelectedSensorPosition(
                Constants.Elevator.kPIDLoopIdx);
    }

    public double getVelocity(Constants.Units units) {
        switch (units) {
            case Ticks:
                return baseMotorMaster.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx);
            case Inches:
                return baseMotorMaster.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx) /
                       Constants.Elevator.TICKS_PER_INCH / 10;
            default:
                return -1;
        }
    }

    public void setPosition(double inches) {
        double ticks = inches * Constants.Elevator.TICKS_PER_INCH;
        baseMotorMaster.set(ControlMode.MotionMagic, ticks);
    }

    public void setPosition(Height height) {
        if (positionMode == PositionMode.Hatch) {
            if (height == Height.Bottom)
                setPosition(Constants.Elevator.Height.hatchBottom);
            else if (height == Height.Middle)
                setPosition(Constants.Elevator.Height.hatchMiddle);
            else if (height == Height.Top)
                setPosition(Constants.Elevator.Height.hatchTop);
        } else  if (positionMode == PositionMode.Cargo) {
            if (height == Height.Bottom)
                setPosition(Constants.Elevator.Height.cargoBottom);
            else if (height == Height.Middle)
                setPosition(Constants.Elevator.Height.cargoMiddle);
            else if (height == Height.Top)
                setPosition(Constants.Elevator.Height.cargoTop);
        }
    }

    public double getHeight() {
        return getEncoderTick() / Constants.Elevator.TICKS_PER_INCH;
    }

    public void togglePositionMode() {
        positionMode = positionMode == PositionMode.Hatch ?
                       PositionMode.Cargo : PositionMode.Hatch;
    }

    public boolean isAtReverseLimit() {
        return baseMotorMaster.getSensorCollection().isRevLimitSwitchClosed();
    }

    public PositionMode getPositionMode() {
        return positionMode;
    }

    public enum PositionMode {
        Hatch, Cargo
    }

    public enum Height {
        Bottom, Middle, Top
    }
}
