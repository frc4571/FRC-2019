package org.usfirst.frc.team4571.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    private static Elevator instance;

    private Elevator() {

    }

    @Override
    protected void initDefaultCommand() {}

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }
}
