@file:JvmName("TalonUtils")

package com.rambots4571.rampage.ctre.motor

import com.ctre.phoenix.ErrorCode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj.DriverStation

fun checkError(errorCode: ErrorCode, message: String) {
    if (errorCode != ErrorCode.OK) {
        DriverStation.reportError(message + errorCode, false)
    }
}

fun TalonSRX.config_PIDF(
    kPIDSlot: Int, kP: Double, kI: Double, kD: Double,
    kF: Double, timeoutMs: Int) {
    config_kF(kPIDSlot, kF, timeoutMs)
    config_kP(kPIDSlot, kP, timeoutMs)
    config_kI(kPIDSlot, kI, timeoutMs)
    config_kD(kPIDSlot, kD, timeoutMs)
}