package org.usfirst.frc.team4571.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import org.usfirst.frc.team4571.robot.Constants
import org.usfirst.frc.team4571.robot.hardware.CanTalon

object DriveSystem : Subsystem() {
    private val leftMaster: WPI_TalonSRX = CanTalon(Constants.DRIVE.LEFT_MASTER)
    private val leftFollower: WPI_TalonSRX = CanTalon(Constants.DRIVE.LEFT_FOLLOWER)
    private val rightMaster: WPI_TalonSRX = CanTalon(Constants.DRIVE.RIGHT_MASTER)
    private val rightFollower: WPI_TalonSRX = CanTalon(Constants.DRIVE.RIGHT_FOLLOWER)

    private val differentialDrive: DifferentialDrive

    private val navx = AHRS(SPI.Port.kMXP)

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
                Constants.Transmission.TICKS_PER_INCH

    val rightDistance
        get() = rightMaster.getSelectedSensorPosition(0) /
                Constants.Transmission.TICKS_PER_INCH

    val heading
        get() = navx.angle

    override fun initDefaultCommand() {}

    fun drive(left: Double, right: Double) =
        differentialDrive.tankDrive(left, right)

    fun stop() = drive(0.0, 0.0)

    fun resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 10)
        rightMaster.setSelectedSensorPosition(0, 0, 10)
    }
}