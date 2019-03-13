package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.rampage.ctre.motor.TalonUtils;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX baseMotorMaster, baseMotorFollower;
    private TalonSRX topMotor;
    private DigitalInput limitSwitch;

    private Elevator() {
        baseMotorMaster = new TalonSRX(Constants.Elevator.BASE_MOTOR_MASTER);
        baseMotorMaster.configFactoryDefault();
        baseMotorMaster.setNeutralMode(NeutralMode.Brake);
        baseMotorMaster.setSensorPhase(true);
        baseMotorMaster.enableCurrentLimit(true);
        baseMotorMaster.configContinuousCurrentLimit(20, Constants.timeoutMs);
        baseMotorMaster.configPeakCurrentLimit(35, Constants.timeoutMs);
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
        //        baseMotorMaster.configMotionCruiseVelocity(Constants.Elevator
        //        .cruiseVel,
        //                                             Constants.Elevator
        //                                             .timeoutMs);
        //        baseMotorMaster.configMotionAcceleration(Constants.Elevator
        //        .acceleration,
        //                                           Constants.Elevator
        //                                           .timeoutMs);

        baseMotorFollower = new TalonSRX(
                Constants.Elevator.BASE_MOTOR_FOLLOWER);
        baseMotorFollower.configFactoryDefault();
        baseMotorFollower.setNeutralMode(NeutralMode.Brake);
        baseMotorFollower.follow(baseMotorMaster);
        baseMotorFollower.setInverted(InvertType.FollowMaster);
        baseMotorFollower.enableCurrentLimit(true);
        baseMotorFollower.configContinuousCurrentLimit(25, Constants.timeoutMs);
        baseMotorFollower.configPeakCurrentLimit(35, Constants.timeoutMs);
        baseMotorFollower.configPeakCurrentDuration(500, Constants.timeoutMs);

        topMotor = new TalonSRX(Constants.Elevator.TOP_MOTOR);
        topMotor.configFactoryDefault();
        topMotor.setNeutralMode(NeutralMode.Brake);
        topMotor.setInverted(true);

        limitSwitch = new DigitalInput(Constants.Elevator.LIMIT_SWITCH);
    }

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {}

    public void teleOpInit() {
        baseMotorMaster.configOpenloopRamp(0.35, Constants.timeoutMs);
        baseMotorFollower.configOpenloopRamp(0.35, Constants.timeoutMs);
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

    public double getHeight() {
        return getEncoderTick() / Constants.Elevator.TICKS_PER_INCH;
    }
}
