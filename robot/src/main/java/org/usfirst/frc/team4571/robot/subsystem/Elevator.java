package org.usfirst.frc.team4571.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpElevator;

public class Elevator extends Subsystem {
    private static Elevator instance;

    private Elevator() {

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
