package org.usfirst.frc.team4571.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import org.usfirst.frc.team4571.robot.Robot
import org.usfirst.frc.team4571.robot.RobotConstants
import org.usfirst.frc.team4571.robot.hardware.CanTalon

object DriveSystem : Subsystem() {
    private val leftFollower: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.FOLLOWER_LEFT_MOTOR)
    private val leftMaster: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.MASTER_LEFT_MOTOR)
    private val rightMaster: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.MASTER_RIGHT_MOTOR)
    private val rightFollower: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.FOLLOWER_RIGHT_MOTOR)

    private val differentialDrive: DifferentialDrive

    private val navx = AHRS(SPI.Port.kMXP)

    init {
        leftMaster.inverted = true
        rightMaster.inverted = true

        leftFollower.follow(leftMaster)
        rightFollower.follow(rightMaster)

        leftFollower.setInverted(InvertType.FollowMaster)
        rightFollower.setInverted(InvertType.FollowMaster)

        differentialDrive = DifferentialDrive(leftFollower, rightMaster)
        differentialDrive.expiration = Robot.period
        differentialDrive.isSafetyEnabled = false

        leftMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10)
        rightMaster.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10)

        rightMaster.setSensorPhase(true)
    }

    val topLeftMotorSpeed
        get() = leftFollower.get()

    val bottomLeftMotorSpeed
        get() = leftMaster.get()

    val topRightMotorSpeed
        get() = rightMaster.get()

    val bottomRightMotorSpeed
        get() = rightFollower.get()

    val leftEncoderTick
        get() = leftMaster.getSelectedSensorPosition(0)

    val rightEncoderTick
        get() = rightMaster.getSelectedSensorPosition(0)

    val heading
        get() = navx.angle

    override fun initDefaultCommand() {}

    fun drive(left: Double, right: Double) =
        differentialDrive.tankDrive(left, right)

    fun stop() = drive(0.0, 0.0)
}