package org.usfirst.frc.team4571.robot.command.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4571.robot.subsystem.Elevator;

public class TeleOpElevator extends Command {
    private static TeleOpElevator instance;
    private Elevator elevator = Elevator.getInstance();

    private TeleOpElevator() {
        requires(elevator);
    }

    public static TeleOpElevator getInstance() {
        if (instance == null) {
            instance = new TeleOpElevator();
        }
        return instance;
    }

    @Override
    protected void execute() {
        // code here
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
