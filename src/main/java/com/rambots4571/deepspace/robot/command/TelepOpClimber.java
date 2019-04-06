package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Climber;
import edu.wpi.first.wpilibj.command.Command;

import static com.rambots4571.deepspace.robot.Robot.rightStick;

public class TelepOpClimber extends Command {
    private Climber climber = Climber.getInstance();

    public TelepOpClimber() {
        requires(climber);
    }

    @Override
    protected void execute() {
        if (rightStick.getPOV() == 0) climber.setClimberMotor(0.5);
        else if (rightStick.getPOV() == 180) climber.setClimberMotor(-0.5);
        else climber.stopClimber();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
