@file:JvmName("TalonSRXFactory")

package com.rambots4571.rampage.ctre.motor

import com.ctre.phoenix.ParamEnum
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.rambots4571.rampage.ctre.Constants

data class Configuration(
    val neutralMode: NeutralMode = NeutralMode.Brake,
    val neutralDeadband: Double = 0.04,
    val enableCurrentLimit: Boolean = false,
    val enableSoftLimit: Boolean = false,
    val enableLimitSwitch: Boolean = false, val forwardSoftLimit: Int = 0,
    val reverseSoftLimit: Int = 0,
    val inverted: Boolean = false, val sensorOutOfPhase: Boolean = false,
    val controlFramePeriodMs: Int = Constants.Talon.timeoutMs,
    val motionControlFramePeriodMs: Int = Constants.Talon.timeoutMs,
    val generalStatusFrameRateMs: Int = Constants.Talon.timeoutMs,
    val feedbackStatusFrameRateMs: Int = Constants.Talon.timeoutMs,
    val magEncoderStatusFrameRateMs: Int = Constants.Talon.timeoutMs,
    val analogTempVBatStatusFrameRateMs: Int = Constants.Talon.timeoutMs,
    val pulseWidthStatusFrameRateMs: Int = Constants.Talon.timeoutMs,
    val velocityMeasPeriod: VelocityMeasPeriod =
        VelocityMeasPeriod.Period_100Ms,
    val velMeasRollingAvgWindow: Int = 64,
    val openLoopRampRate: Double = 0.0, val closedLoopRampRate: Double = 0.0)

private const val kTimeoutMs = Constants.Talon.timeoutMs
private val defaultConfig = Configuration()

fun createTalon(id: Int, config: Configuration): TalonSRX {
    val talon = TalonSRX(id)
    talon.set(ControlMode.PercentOutput, 0.0)

    talon.changeMotionControlFramePeriod(config.motionControlFramePeriodMs)
    talon.clearMotionProfileHasUnderrun(kTimeoutMs)
    talon.clearMotionProfileTrajectories()

    talon.clearStickyFaults(kTimeoutMs)

    talon.configForwardLimitSwitchSource(
            LimitSwitchSource.FeedbackConnector,
            LimitSwitchNormal.NormallyOpen, kTimeoutMs)
    talon.configReverseLimitSwitchSource(
            LimitSwitchSource.FeedbackConnector,
            LimitSwitchNormal.NormallyOpen, kTimeoutMs)
    talon.overrideLimitSwitchesEnable(config.enableLimitSwitch)

    // Turn off re-zeroing by default.
    talon.configSetParameter(
            ParamEnum.eClearPositionOnLimitF, 0.0, 0, 0,
            kTimeoutMs)
    talon.configSetParameter(
            ParamEnum.eClearPositionOnLimitR, 0.0, 0, 0,
            kTimeoutMs)

    talon.configNominalOutputForward(0.0, kTimeoutMs)
    talon.configNominalOutputReverse(0.0, kTimeoutMs)
    talon.configNeutralDeadband(config.neutralDeadband, kTimeoutMs)

    talon.configPeakOutputForward(1.0, kTimeoutMs)
    talon.configPeakOutputReverse(-1.0, kTimeoutMs)

    talon.setNeutralMode(config.neutralMode)

    talon.configForwardSoftLimitThreshold(
            config.forwardSoftLimit,
            kTimeoutMs)
    talon.configForwardSoftLimitEnable(
            config.enableSoftLimit,
            kTimeoutMs)

    talon.configReverseSoftLimitThreshold(
            config.reverseSoftLimit,
            kTimeoutMs)
    talon.configReverseSoftLimitEnable(
            config.enableSoftLimit,
            kTimeoutMs)
    talon.overrideSoftLimitsEnable(config.enableSoftLimit)

    talon.inverted = config.inverted
    talon.setSensorPhase(config.sensorOutOfPhase)

    talon.selectProfileSlot(0, 0)

    talon.configVelocityMeasurementPeriod(
            config.velocityMeasPeriod, kTimeoutMs)
    talon.configVelocityMeasurementWindow(
            config.velMeasRollingAvgWindow, kTimeoutMs)

    talon.configOpenloopRamp(
            config.openLoopRampRate,
            kTimeoutMs)
    talon.configClosedloopRamp(
            config.closedLoopRampRate,
            kTimeoutMs)

    talon.configVoltageCompSaturation(0.0, kTimeoutMs)
    talon.configVoltageMeasurementFilter(32, kTimeoutMs)
    talon.enableVoltageCompensation(false)

    talon.enableCurrentLimit(config.enableCurrentLimit)

    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_1_General,
            config.generalStatusFrameRateMs,
            kTimeoutMs)
    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_2_Feedback0,
            config.feedbackStatusFrameRateMs,
            kTimeoutMs)

    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_3_Quadrature,
            config.magEncoderStatusFrameRateMs,
            kTimeoutMs)
    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_4_AinTempVbat,
            config.analogTempVBatStatusFrameRateMs,
            kTimeoutMs)
    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_8_PulseWidth,
            config.pulseWidthStatusFrameRateMs,
            kTimeoutMs)

    talon.setControlFramePeriod(
            ControlFrame.Control_3_General, config.controlFramePeriodMs)

    talon.setStatusFramePeriod(
            StatusFrameEnhanced.Status_10_MotionMagic,
            config.motionControlFramePeriodMs, kTimeoutMs)

    return talon
}

fun createDefaultTalon(id: Int): TalonSRX {
    return createTalon(id, defaultConfig)
}