package org.usfirst.frc.team4571.robot.hardware

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import org.usfirst.frc.team4571.robot.Constants

class CanTalon(deviceNumber: Int) : WPI_TalonSRX(deviceNumber) {
    init {
        expiration = Constants.ROBOT_PERIOD
        isSafetyEnabled = false
        setNeutralMode(NeutralMode.Brake)
    }
}

fun WPI_TalonSRX.config_PIDF(kP: Double, kI: Double, kD: Double, kF: Double) {
    this.config_kP(0, kP, Constants.periodMs)
    this.config_kI(0, kI, Constants.periodMs)
    this.config_kD(0, kD, Constants.periodMs)
    this.config_kF(0, kF, Constants.periodMs)
}