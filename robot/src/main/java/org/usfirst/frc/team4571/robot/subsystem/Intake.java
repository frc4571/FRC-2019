package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;

public class Intake extends Subsystem {
    private static Intake instance;
    private VictorSPX leftMotor;
    private VictorSPX rightMotor;

    private Intake() {
        leftMotor = new VictorSPX(Constants.Intake.LEFT_MOTOR);
        rightMotor = new VictorSPX(Constants.Intake.RIGHT_MOTOR);
        rightMotor.follow(leftMotor);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {}

    public void setPower(double value) {
        leftMotor.set(ControlMode.PercentOutput, value);
    }
}
