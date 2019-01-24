package org.usfirst.frc.team4571.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.hardware.CanTalon;

public final class DriveSystem extends Subsystem {
    private WPI_TalonSRX leftMaster = new CanTalon(Constants.DRIVE.LEFT_MASTER);
    private WPI_TalonSRX leftFollower = new CanTalon(
            Constants.DRIVE.LEFT_FOLLOWER);
    private WPI_TalonSRX rightMaster = new CanTalon(
            Constants.DRIVE.RIGHT_MASTER);
    private WPI_TalonSRX rightFollower = new CanTalon(
            Constants.DRIVE.RIGHT_FOLLOWER);

    private AHRS navx = new AHRS(SPI.Port.kMXP);

    private DifferentialDrive differentialDrive = new
            DifferentialDrive(leftMaster, rightMaster);

    private class TurnOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            drive(0, 0);
        }
    }

    private PIDController turnController =
            new PIDController(Constants.DRIVE.Turn.kP,
                              Constants.DRIVE.Turn.kI,
                              Constants.DRIVE.Turn.kD,
                              navx, new TurnOutput());

    public DriveSystem() {
        leftMaster.setInverted(true);
        rightMaster.setInverted(true);
        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster);
        differentialDrive.setExpiration(Constants.period);
        differentialDrive.setSafetyEnabled(false);

        leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.periodMs);
        rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.periodMs);

        rightMaster.setSensorPhase(true);

        turnController.setInputRange(-180.0, 180.0);
        turnController.setOutputRange(-0.8, 0.8);
        turnController.setAbsoluteTolerance(3.0);
    }

    @Override
    protected void initDefaultCommand() {}

    public double getLeftMasterSpeed() {
        return leftMaster.get();
    }

    public double getLeftFollowerSpeed() {
        return leftFollower.get();
    }

    public double getRightMasterSpeed() {
        return rightMaster.get();
    }

    public double getRightFollowerSpeed() {
        return rightFollower.get();
    }

    public int getLeftEncoderTick() {
        return leftMaster.getSelectedSensorPosition(0);
    }

    public int getRightEncoderTick() {
        return rightMaster.getSelectedSensorPosition(0);
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

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public double getHeading() {
        return navx.getAngle();
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

    public WPI_TalonSRX getLeftMaster() {
        return leftMaster;
    }

    public WPI_TalonSRX getRightMaster() {
        return rightMaster;
    }

    public void setTalonsFactoryDefault() {
        leftMaster.configFactoryDefault(Constants.periodMs);
        rightMaster.configFactoryDefault(Constants.periodMs);
        leftFollower.configFactoryDefault(Constants.periodMs);
        rightFollower.configFactoryDefault(Constants.periodMs);
    }

    public void configMPGains() {
        ((CanTalon)leftMaster).config_PIDF(Constants.MPGains.kP,
                                           Constants.MPGains.kI,
                                           Constants.MPGains.kD,
                                           Constants.MPGains.kF);
        ((CanTalon)rightMaster).config_PIDF(Constants.MPGains.kP,
                                           Constants.MPGains.kI,
                                           Constants.MPGains.kD,
                                           Constants.MPGains.kF);
    }

    public void configTrajPointPeriod() {
        leftMaster.configMotionProfileTrajectoryPeriod(
                Constants.MP.trajectoryPointPeriod, Constants.periodMs);
        rightMaster.configMotionProfileTrajectoryPeriod(
                Constants.MP.trajectoryPointPeriod, Constants.periodMs);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic,
                                        Constants.MP.trajectoryPointPeriod,
                                        Constants.periodMs);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic,
                                         Constants.MP.trajectoryPointPeriod,
                                         Constants.periodMs);
    }

    public void setPercentOutput() {
        leftMaster.set(ControlMode.PercentOutput, 0);
        rightMaster.set(ControlMode.PercentOutput, 0);
    }

    public void setMPOutput(int value) {
        leftMaster.set(ControlMode.MotionProfile, value);
        rightMaster.set(ControlMode.MotionProfile, value);
    }

    public void drive(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }

    public void stop() {
        drive(0, 0);
    }
}