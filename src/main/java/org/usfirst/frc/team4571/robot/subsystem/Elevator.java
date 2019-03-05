package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.rambots4571.rampage.ctre.motor.TalonUtilsKt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX baseMotor;
    private TalonSRX topMotor;
    private DigitalInput limitSwitch;

    private Elevator() {
        baseMotor = new TalonSRX(Constants.Elevator.BASE_MOTOR);
        baseMotor.configFactoryDefault();
        topMotor = new TalonSRX(Constants.Elevator.TOP_MOTOR);
        topMotor.configFactoryDefault();
        baseMotor.setNeutralMode(NeutralMode.Brake);
        topMotor.setNeutralMode(NeutralMode.Brake);
        limitSwitch = new DigitalInput(Constants.Elevator.LIMIT_SWITCH);

        baseMotor.setInverted(true);
        baseMotor.setSensorPhase(true);

        baseMotor.enableCurrentLimit(true);
        baseMotor.configContinuousCurrentLimit(20, Constants.Elevator.timeoutMs);
        baseMotor.configPeakCurrentLimit(40, Constants.Elevator.timeoutMs);

        baseMotor.configNeutralDeadband(0.06, Constants.Elevator.timeoutMs);

        baseMotor.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.Elevator.kPIDLoopIdx, Constants.Elevator.timeoutMs);

        baseMotor.setStatusFramePeriod(
                StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
                Constants.Elevator.timeoutMs);
        baseMotor.setStatusFramePeriod(
                StatusFrameEnhanced.Status_10_MotionMagic, 10,
                Constants.Elevator.timeoutMs);

        baseMotor.configNominalOutputForward(0, Constants.Elevator.timeoutMs);
        baseMotor.configNominalOutputReverse(0, Constants.Elevator.timeoutMs);
        baseMotor.configPeakOutputForward(1, Constants.Elevator.timeoutMs);
        baseMotor.configPeakOutputReverse(-1, Constants.Elevator.timeoutMs);

        baseMotor.selectProfileSlot(
                Constants.Elevator.kSlotIdx,
                Constants.Elevator.kPIDLoopIdx);

        TalonUtilsKt.config_PIDF(baseMotor, Constants.Elevator.kPIDLoopIdx,
                                 Constants.Elevator.Gains.kP,
                                 Constants.Elevator.Gains.kI,
                                 Constants.Elevator.Gains.kD,
                                 Constants.Elevator.Gains.kF,
                                 Constants.Elevator.timeoutMs);

        //        baseMotor.configMotionCruiseVelocity(Constants.Elevator
        //        .cruiseVel,
        //                                             Constants.Elevator
        //                                             .timeoutMs);
        //        baseMotor.configMotionAcceleration(Constants.Elevator
        //        .acceleration,
        //                                           Constants.Elevator
        //                                           .timeoutMs);
    }

    @Override
    protected void initDefaultCommand() {}

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    public void teleOpInit() {
        baseMotor.configOpenloopRamp(0.35, Constants.Elevator.timeoutMs);
    }

    public void setBaseMotor(double value) {
        baseMotor.set(ControlMode.PercentOutput, value);
    }

    public void setTopMotor(double value) {
        topMotor.set(ControlMode.PercentOutput, value);
    }

    public void stopBaseMotor() {
        baseMotor.set(ControlMode.PercentOutput, 0);
    }

    public void stopTopMotor() {
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLimitSwitchPressed() {
        return limitSwitch.get();
    }

    public void resetEncoder() {
        baseMotor.setSelectedSensorPosition(0);
    }

    public int getEncoderTick() {
        return baseMotor.getSelectedSensorPosition(
                Constants.Elevator.kPIDLoopIdx);
    }

    public double getVelocity(Constants.Units units) {
        switch (units) {
            case Ticks:
                return baseMotor.getSelectedSensorVelocity(
                        Constants.Elevator.kPIDLoopIdx);
            case Inches:
                return baseMotor.getSelectedSensorVelocity(
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
