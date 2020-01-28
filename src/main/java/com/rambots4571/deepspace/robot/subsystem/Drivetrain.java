package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.component.DriveTalon;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance;
    public final AHRS navx;
    private WPI_TalonSRX leftMaster;
    private WPI_TalonSRX leftFollower1;
    private WPI_TalonSRX leftFollower2;
    private WPI_TalonSRX rightMaster;
    private WPI_TalonSRX rightFollower1;
    private WPI_TalonSRX rightFollower2;
    private NeutralMode neutralMode = NeutralMode.Coast;
    private DifferentialDrive drive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveOdometry odometry;
    private Pose2d pose;

    private Drivetrain() {
        leftMaster = new DriveTalon(Constants.Drive.LEFT_MASTER, neutralMode);
        leftMaster.setInverted(true);

        rightMaster = new DriveTalon(Constants.Drive.RIGHT_MASTER, neutralMode);

        leftFollower1 = new DriveTalon(
                Constants.Drive.LEFT_FOLLOWER1, neutralMode);
        leftFollower1.follow(leftMaster);
        leftFollower1.setInverted(InvertType.FollowMaster);

        leftFollower2 = new DriveTalon(
                Constants.Drive.LEFT_FOLLOWER2, neutralMode);
        leftFollower2.follow(leftMaster);
        leftFollower2.setInverted(InvertType.FollowMaster);

        rightFollower1 = new DriveTalon(
                Constants.Drive.RIGHT_FOLLOWER1, neutralMode);
        rightFollower1.follow(rightMaster);
        rightFollower1.setInverted(InvertType.FollowMaster);

        rightFollower2 = new DriveTalon(
                Constants.Drive.RIGHT_FOLLOWER2, neutralMode);
        rightFollower2.follow(rightMaster);
        rightFollower2.setInverted(InvertType.FollowMaster);

        drive = new DifferentialDrive(leftMaster, rightMaster);
        drive.setSafetyEnabled(false);

        navx = new AHRS(SPI.Port.kMXP);

        kinematics = new DifferentialDriveKinematics(
                Constants.Drive.trackWidth);

        resetEncoders();
        odometry = new DifferentialDriveOdometry(getHeading());
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
    public void periodic() {
        odometry.update(
                getHeading(), Units.inchesToMeters(getLeftDistance()),
                Units.inchesToMeters(getRightDistance()));
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Gyro Heading", this::getAngle, null);
        builder.addDoubleProperty(
                "Left Distance (in)", this::getLeftDistance, null);
        builder.addDoubleProperty(
                "Right Distance (in)", this::getRightDistance, null);
        builder.addDoubleProperty(
                "Right Speed", this::getLeftSpeedMetersPerSec, null);
        builder.addDoubleProperty(
                "Left Speed", this::getRightSpeedMetersPerSec, null);
    }

    /**
     * @return raw encoder ticks of the left side.
     */
    public int getLeftEncoderTick() {
        return leftMaster.getSelectedSensorPosition(
                Constants.Drive.highGearPIDSlotIdx);
    }

    /**
     * @return raw encoder ticks of the right side.
     */
    public int getRightEncoderTick() {
        return rightMaster.getSelectedSensorPosition(
                Constants.Drive.highGearPIDSlotIdx);
    }

    /**
     * @return distance in inches of the left side.
     */
    public double getLeftDistance() {
        return getLeftEncoderTick() /
               Constants.Drive.Transmission.TICKS_PER_INCH;
    }

    /**
     * @return distance in inches of the right side.
     */
    public double getRightDistance() {
        return getRightEncoderTick() /
               Constants.Drive.Transmission.TICKS_PER_INCH;
    }

    /**
     * @return the velocity of the left side in inches per second.
     */
    public double getLeftVelocity() {
        return leftMaster.getSelectedSensorVelocity(
                Constants.Drive.highGearPIDSlotIdx) /
               Constants.Drive.Transmission.TICKS_PER_INCH * 10;
    }

    /**
     * @return the velocity of the right side in inches per second.
     */
    public double getRightVelocity() {
        return rightMaster.getSelectedSensorVelocity(
                Constants.Drive.highGearPIDSlotIdx) /
               Constants.Drive.Transmission.TICKS_PER_INCH * 10;
    }

    private double getLeftSpeedMetersPerSec() {
        return Units.inchesToMeters(getLeftVelocity());
    }

    private double getRightSpeedMetersPerSec() {
        return Units.inchesToMeters(getRightVelocity());
    }

    public double getAngle() {
        return navx.getAngle();
    }

    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-getAngle());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                getLeftSpeedMetersPerSec(),
                getRightSpeedMetersPerSec());
    }

    public DifferentialDriveVoltageConstraint getVoltageConstraint() {
        return new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                        Constants.Drive.ksVolts,
                        Constants.Drive.kvVoltsSecPerMeter,
                        Constants.Drive.kaVoltSecSquaredPerMeter),
                Drivetrain.getInstance().getKinematics(), 10);
    }

    public TrajectoryConfig getTrajectoryConfig() {
        return new TrajectoryConfig(
                Units.feetToMeters(Constants.Drive.maxVel),
                Units.feetToMeters(Constants.Drive.maxAccel)).setKinematics(
                Drivetrain.getInstance().getKinematics()).addConstraint(
                Drivetrain.getInstance().getVoltageConstraint());
    }

    public void resetGyro() {
        navx.reset();
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public void drive(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
        drive.feed();
    }

    public void setVoltage(double left, double right) {
        leftMaster.setVoltage(left);
        rightMaster.setVoltage(right);
        drive.feed();
    }

    public void setNeutralMode(NeutralMode mode) {
        leftMaster.setNeutralMode(mode);
        leftFollower1.setNeutralMode(mode);
        leftFollower2.setNeutralMode(mode);
        rightMaster.setNeutralMode(mode);
        rightFollower1.setNeutralMode(mode);
        rightFollower2.setNeutralMode(mode);
    }

    public void stop() {
        drive(0, 0);
    }
}