package com.rambots4571.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.rambots4571.deepspace.robot.Constants;
import com.rambots4571.deepspace.robot.command.TeleOpIntake;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
    private static Intake instance;
    private TalonSRX leftIntakeMotor;
    private TalonSRX pulleyMotor;
    private TalonSRX hatchMotor;

    private Intake() {
        leftIntakeMotor = new TalonSRX(Constants.Intake.LEFT_INTAKE_MOTOR);
        leftIntakeMotor.setNeutralMode(NeutralMode.Brake);

        VictorSPX rightIntakeMotor = new VictorSPX(
                Constants.Intake.RIGHT_INTAKE_MOTOR);
        rightIntakeMotor.setNeutralMode(NeutralMode.Brake);
        rightIntakeMotor.follow(leftIntakeMotor);
        rightIntakeMotor.setInverted(InvertType.OpposeMaster);

        pulleyMotor = new TalonSRX(Constants.Intake.PULLEY_MOTOR);
        pulleyMotor.setInverted(true);
        pulleyMotor.setNeutralMode(NeutralMode.Brake);

        hatchMotor = new TalonSRX(Constants.Intake.HATCH_MOTOR);
        hatchMotor.setNeutralMode(NeutralMode.Brake);
    }

    public static Intake getInstance() {
        if (instance == null) {
            synchronized (Intake.class) {
                instance = new Intake();
            }
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleOpIntake());
    }

    public void setIntakePower(double value) {
        leftIntakeMotor.set(ControlMode.PercentOutput, value);
    }

    public void setPulleyPower(double value) {
        pulleyMotor.set(ControlMode.PercentOutput, value);
    }

    public void setHatchMotor(double value) {
        hatchMotor.set(ControlMode.PercentOutput, value);
    }
}