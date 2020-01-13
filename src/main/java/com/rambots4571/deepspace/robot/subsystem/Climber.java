package com.rambots4571.deepspace.robot.subsystem;

import com.rambots4571.deepspace.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private static Climber instance;
    private CANSparkMax climberMotor;

    private Climber() {
        climberMotor = new CANSparkMax(
                Constants.Climber.CLIMBER_MOTOR,
                CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public static Climber getInstance() {
        if (instance == null) {
            synchronized (Climber.class) {
                instance = new Climber();
            }
        }
        return instance;
    }

    public void setClimberMotor(double value) {
        climberMotor.set(value);
    }

    public void stopClimber() {
        climberMotor.disable();
    }
}
