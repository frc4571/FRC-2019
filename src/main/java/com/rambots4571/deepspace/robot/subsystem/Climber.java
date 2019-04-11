package com.rambots4571.deepspace.robot.subsystem;

import com.rambots4571.deepspace.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
    private static Climber instance;
    private CANSparkMax climberMotor;

    private Climber() {
        climberMotor = new CANSparkMax(Constants.Climber.CLIMBER_MOTOR,
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

    @Override
    protected void initDefaultCommand() {

    }

    public void setClimberMotor(double value) {
        climberMotor.set(value);
    }

    public void stopClimber() {
        climberMotor.disable();
    }
}
