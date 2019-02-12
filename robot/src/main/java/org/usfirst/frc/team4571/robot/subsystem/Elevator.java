package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpElevator;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX elevatorMotor, topelevatorMotor;


    private Elevator() {
        elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_MOTOR);
        topelevatorMotor= new TalonSRX(RobotMap.TOPELEVATOR_MOTOR);

        elevatorMotor.setExperation(Robot.DEFAULT_PERIOD);
        topelevatorMotor.setExperation(Robot.DEFAULT_PERIOD);

        elevatorMotor.setSafteyEnabled(false);
        topelevatorMotor.setSafteyEnabled(false);

        elevatorMotor.satNeutralMode(NeutralMode.Brake);
        topelevatorMotor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(TeleOpElevator.getInstance());
    }

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }
}
