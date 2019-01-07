package org.usfirst.frc.team4571.robot.subsystems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import org.usfirst.frc.team4571.robot.RobotConstants
import org.usfirst.frc.team4571.robot.hardware.CanTalon

object DriveSystem : Subsystem() {
    private val topLeftMotor: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.TOP_LEFT_MOTOR)
    private val bottomLeftMotor: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.BOTTOM_LEFT_MOTOR)
    private val topRightMotor: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.TOP_RIGHT_MOTOR)
    private val bottomRightMotor: WPI_TalonSRX = CanTalon(RobotConstants.DRIVE.BOTTOM_RIGHT_MOTOR)

    private val differentialDrive: DifferentialDrive

    private val navx = AHRS(SPI.Port.kMXP)

    init {
        bottomLeftMotor.follow(topLeftMotor)
        bottomRightMotor.follow(topRightMotor)
        differentialDrive = DifferentialDrive(topLeftMotor, topRightMotor)
    }

    val topLeftMotorSpeed
        get() = topLeftMotor.get()

    val bottomLeftMotorSpeed
        get() = bottomLeftMotor.get()

    val topRightMotorSpeed
        get() = topRightMotor.get()

    val bottomRightMotorSpeed
        get() = bottomRightMotor.get()

    val heading
        get() = navx.angle

    override fun initDefaultCommand() {}

    fun drive(left: Double, right: Double) =
        differentialDrive.tankDrive(left, right)

    fun stop() = drive(0.0, 0.0)
}