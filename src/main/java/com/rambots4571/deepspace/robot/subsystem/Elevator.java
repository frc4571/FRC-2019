package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.command.TeleOpElevator;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import com.rambots4571.rampage.function.SwitchAction;
import com.rambots4571.rampage.sensor.pid.Tuner;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.rambots4571.deepspace.robot.Constants.Elevator.Gains.*;
import static com.rambots4571.deepspace.robot.Constants.Elevator.Height.*;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private static Map<Position,Double> heights = new HashMap<>();

    static {
        heights.put(new Position(PositionMode.Hatch, Height.Zero), 0.0);
        heights.put(
                new Position(PositionMode.Hatch, Height.Bottom), hatchBottom);
        heights.put(
                new Position(PositionMode.Hatch, Height.Middle), hatchMiddle);
        heights.put(new Position(PositionMode.Hatch, Height.Top), hatchTop);
        heights.put(new Position(PositionMode.Cargo, Height.Zero), 0.0);
        heights.put(
                new Position(PositionMode.Cargo, Height.Bottom), cargoBottom);
        heights.put(
                new Position(PositionMode.Cargo, Height.Middle), cargoMiddle);
        heights.put(new Position(PositionMode.Cargo, Height.Top), cargoTop);
    }

    private TalonSRX baseMotorMaster;
    private TalonSRX topMotor;
    private Position position;
    private Tuner tuner;
    private ControlMode controlMode;
    private SwitchAction<ControlMode> statePrinter;
    private SwitchAction<Position> positionPrinter;
    private double ticksPerInch = Constants.Elevator.TICKS_PER_INCH;
    private double acceleration;
    private double maxAcceleration;
    private double vel;
    private double maxVel;
    private double prevVel;

    private Elevator() {
        super("Elevator");
        baseMotorMaster = new TalonSRX(Constants.Elevator.BASE_MOTOR_MASTER);
        baseMotorMaster.configFactoryDefault();
        baseMotorMaster.setInverted(true);
        baseMotorMaster.setSensorPhase(true);
        baseMotorMaster.setNeutralMode(NeutralMode.Brake);
        baseMotorMaster.enableCurrentLimit(true);
        baseMotorMaster.configContinuousCurrentLimit(25, Constants.timeoutMs);
        baseMotorMaster.configPeakCurrentLimit(30, Constants.timeoutMs);
        baseMotorMaster.configPeakCurrentDuration(500, Constants.timeoutMs);
        baseMotorMaster.configNeutralDeadband(0.06, Constants.timeoutMs);
        baseMotorMaster.configOpenloopRamp(0.35, Constants.timeoutMs);
        configMotionMagic();

        TalonSRX baseMotorFollower = new TalonSRX(
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

        tuner = new Tuner(kP, kI, kD, kF);

        position = new Position(PositionMode.Hatch, Height.Zero);

        resetEncoder();

        maxAcceleration = 0;
        maxVel = 0;
        prevVel = 0;

        statePrinter = new SwitchAction<>(
                () -> controlMode, ControlMode.Disabled);
        positionPrinter = new SwitchAction<>(this::getPosition, position);
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
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty(
                "Ticks per Inch", () -> ticksPerInch,
                value -> ticksPerInch = value);
        builder.addStringProperty(
                "Position Mode", () -> position.mode.toString(), null);
        builder.addDoubleProperty("Encoder Tick", this::getEncoderTick, null);
        builder.addDoubleProperty("Elevator Height", this::getHeight, null);
        builder.addDoubleProperty("Raw Velocity (u/100ms)", () -> vel, null);
        builder.addDoubleProperty(
                "Raw Max Velocity (u/100ms)", () -> maxVel, null);
        builder.addDoubleProperty(
                "Raw Acceleration (u/100ms^2)", () -> acceleration, null);
        builder.addDoubleProperty(
                "Raw Max Acceleration", () -> maxAcceleration, null);
    }

    private void configMotionMagic() {
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
                kP, kI, kD, kF, Constants.timeoutMs);
        baseMotorMaster.configMotionCruiseVelocity(
                Constants.Elevator.cruiseVel, Constants.timeoutMs);
        baseMotorMaster.configMotionAcceleration(
                Constants.Elevator.acceleration, Constants.timeoutMs);
    }

    private void setPIDF(double kP, double kI, double kD, double kF) {
        TalonUtils.config_PIDF(
                baseMotorMaster, Constants.Elevator.kPIDLoopIdx, kP, kI, kD, kF,
                Constants.timeoutMs);
    }

    @Override
    public void periodic() {
        vel = getVelocity(Constants.Units.Ticks);
        acceleration = (vel - prevVel) / 0.02;
        prevVel = vel;
        if (Math.abs(acceleration) > Math.abs(maxAcceleration))
            maxAcceleration = Math.abs(acceleration);
        if (Math.abs(vel) > Math.abs(maxVel)) maxVel = Math.abs(vel);
        statePrinter.run(
                () -> System.out
                        .println("Elevator Control Mode: " +
                                 controlMode));
        positionPrinter.run(
                () -> System.out.println("Elevator Position: " + position));
    }

    public void teleOpInit() {
        position = new Position(PositionMode.Hatch, Height.Zero);
        setPIDF(tuner.getKP(), tuner.getKI(), tuner.getKD(), tuner.getKF());
    }

    public void setBaseMotor(double value) {
        baseMotorMaster.set(ControlMode.PercentOutput, value);
    }

    public void setTopMotor(double value) {
        controlMode = ControlMode.PercentOutput;
        topMotor.set(controlMode, value);
    }

    public void stopBaseMotor() {
        baseMotorMaster.set(ControlMode.PercentOutput, 0);
    }

    public void stopTopMotor() {
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public void resetEncoder() {
        baseMotorMaster.setSelectedSensorPosition(0);
    }

    public int getEncoderTick() {
        return baseMotorMaster.getSelectedSensorPosition(
                Constants.Elevator.kPIDLoopIdx);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(double inches) {
        controlMode = ControlMode.MotionMagic;
        double ticks = inches * ticksPerInch;
        baseMotorMaster.set(controlMode, ticks);
    }

    public void setPosition(Height height) {
        position.height = height;
        setPosition(heights.get(position));
    }

    /**
     * Ensures that the height is in between 0 < height < max height.
     *
     * @param inches target height
     * @return clamped height
     */
    public double clamp(double inches) {
        return Math.min(Math.max(0, inches), hatchTop);
    }

    public double getVelocity(Constants.Units units) {
        switch (units) {
            case Ticks: // u / 100ms
                return baseMotorMaster.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx);
            case Inches: // inches / s
                return baseMotorMaster.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx) / ticksPerInch / 10.0;
            case Feet: // feet / s
                return baseMotorMaster.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx) / ticksPerInch / 120.0;
            default:
                return -1;
        }
    }

    public double getHeight() {
        return getEncoderTick() / ticksPerInch;
    }

    public void togglePositionMode() {
        position.mode = position.mode == PositionMode.Hatch ?
                        PositionMode.Cargo : PositionMode.Hatch;
    }

    public enum PositionMode {
        Hatch, Cargo
    }

    public enum Height {
        Zero, Bottom, Middle, Top
    }

    public static class Position {
        private PositionMode mode;
        private Height height;

        public Position(PositionMode mode, Height height) {
            this.mode = mode;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return mode == position.mode &&
                   height == position.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mode, height);
        }

        @Override
        public String toString() {
            return "Position{" +
                   "mode=" + mode +
                   ", height=" + height +
                   '}';
        }
    }
}
