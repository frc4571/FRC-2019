package org.usfirst.frc.team4571.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.StatusFrame
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import org.usfirst.frc.team4571.robot.Constants
import org.usfirst.frc.team4571.robot.MotionProfile
import org.usfirst.frc.team4571.robot.hardware.CanTalon
import org.usfirst.frc.team4571.robot.hardware.config_PIDF

object DriveSystem : Subsystem() {
    val leftMaster: WPI_TalonSRX = CanTalon(Constants.DRIVE.LEFT_MASTER)
    val rightMaster: WPI_TalonSRX = CanTalon(Constants.DRIVE.RIGHT_MASTER)
    private val leftFollower: WPI_TalonSRX = CanTalon(Constants.DRIVE.LEFT_FOLLOWER)
    private val rightFollower: WPI_TalonSRX = CanTalon(Constants.DRIVE.RIGHT_FOLLOWER)

    private val differentialDrive: DifferentialDrive

    private val navx = AHRS(SPI.Port.kMXP)

    private object Gyro {
        const val kP = 0.0
        const val kI = 0.0
        const val kD = 0.0
    }

    private object TurnOutput : PIDOutput {
        override fun pidWrite(output: Double) = drive(-output, output)
    }

    val turnController: PIDController =
        PIDController(Gyro.kP, Gyro.kI, Gyro.kD, navx, TurnOutput)

    init {
        leftMaster.inverted = true
        rightMaster.inverted = true

        leftFollower.follow(leftMaster)
        rightFollower.follow(rightMaster)

        leftFollower.setInverted(InvertType.FollowMaster)
        rightFollower.setInverted(InvertType.FollowMaster)

        differentialDrive = DifferentialDrive(leftMaster, rightMaster)
        differentialDrive.expiration = Constants.ROBOT_PERIOD
        differentialDrive.isSafetyEnabled = false

        leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10)
        rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10)

        rightMaster.setSensorPhase(true)

        turnController.setInputRange(-180.0, 180.0)
        turnController.setOutputRange(-0.8, 0.8)
        turnController.setAbsoluteTolerance(3.0)
    }

    val leftFollowerSpeed
        get() = leftFollower.get()

    val leftMasterSpeed
        get() = leftMaster.get()

    val rightMasterSpeed
        get() = rightMaster.get()

    val rightFollowerSpeed
        get() = rightFollower.get()

    val leftEncoderTick
        get() = leftMaster.getSelectedSensorPosition(0)

    val rightEncoderTick
        get() = rightMaster.getSelectedSensorPosition(0)

    val leftDistance
        get() = leftMaster.getSelectedSensorPosition(0) /
                Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH

    val rightDistance
        get() = rightMaster.getSelectedSensorPosition(0) /
                Constants.Transmission.HIGH_GEAR_TICKS_PER_INCH

    val heading
        get() = navx.angle

    val isOnAngle
        get() = turnController.onTarget()

    override fun initDefaultCommand() {}

    fun drive(left: Double, right: Double) =
        differentialDrive.tankDrive(left, right)

    fun stop() = drive(0.0, 0.0)

    fun resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 10)
        rightMaster.setSelectedSensorPosition(0, 0, 10)
    }

    fun turnToAngle(angle: Double) {
        turnController.reset()
        turnController.setpoint = angle
        turnController.enable()
    }

    fun resetGyro() {
        navx.reset()
    }

    fun setTalonsFactoryDefault() {
        leftMaster.configFactoryDefault(20)
        rightMaster.configFactoryDefault(20)
        leftFollower.configFactoryDefault(20)
        rightFollower.configFactoryDefault(20)
    }

    fun configMPGains() {
        leftMaster.config_PIDF(Constants.MPGains.kP, Constants.MPGains.kI,
                               Constants.MPGains.kD, Constants.MPGains.kF)
        rightMaster.config_PIDF(Constants.MPGains.kP, Constants.MPGains.kI,
                                Constants.MPGains.kD, Constants.MPGains.kF)
    }

    fun configTrajPointPeriod() {
        leftMaster.configMotionProfileTrajectoryPeriod(
                Constants.MP.trajectoryPointPeriod, Constants.periodMs)
        rightMaster.configMotionProfileTrajectoryPeriod(
                Constants.MP.trajectoryPointPeriod, Constants.periodMs)
        leftMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic,
                                        Constants.MP.trajectoryPointPeriod,
                                        Constants.periodMs)
        rightMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic,
                                         Constants.MP.trajectoryPointPeriod,
                                         Constants.periodMs)
    }

    fun setMPOutput(value: Int) {
        leftMaster.set(ControlMode.MotionProfile, value.toDouble())
        rightMaster.set(ControlMode.MotionProfile, value.toDouble())
    }

    fun setPercentOuput() {
        leftMaster.set(ControlMode.PercentOutput, 0.0)
        rightMaster.set(ControlMode.PercentOutput, 0.0)
    }
}