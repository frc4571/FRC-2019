package com.rambots4571.deepspace.robot.component;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.rambots4571.deepspace.robot.Constants;

public class DriveTalon extends WPI_TalonSRX {
    /**
     * Constructor for TalonSRX object
     *
     * @param deviceNumber CAN Device ID of Device
     */
    public DriveTalon(int deviceNumber, NeutralMode mode) {
        super(deviceNumber);
        configFactoryDefault();
        configNeutralDeadband(Constants.Drive.deadband);
        setNeutralMode(mode);
        configOpenloopRamp(Constants.Drive.openLoopRampRate, Constants.timeoutMs);
        enableCurrentLimit(true);
        configContinuousCurrentLimit(Constants.Drive.peakCurrent, Constants.timeoutMs);
        configPeakCurrentLimit(Constants.Drive.peakCurrent, Constants.timeoutMs);
        configPeakCurrentDuration(0, Constants.timeoutMs);
    }
}
