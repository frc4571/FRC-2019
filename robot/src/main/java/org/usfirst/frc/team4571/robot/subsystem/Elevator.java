package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpElevator;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX elevatorMotor, toplevatorMotor;


    private Elevator() {
        this.elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_MOTOR);
        this.topelevatorMotor= new TalonSRX(RobotMap.TOPELEVATOR_MOTOR);

        this.elevatorMotor.setExperation(Robot.DEFAULT_PERIOD);
        this.topelevatorMotor.setExperation(Robot.DEFAULT_PERIOD);

        this.elevatorMotor.setSafteyEnabled(false);
        this.topelevatorMotor.setSafteyEnabled(false);

        this.elevatorMotor.satNeutralMode(NeutralMode.Brake);
        this.topelevatorMotor.setNeutralMode(NeutralMode.Brake);
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
