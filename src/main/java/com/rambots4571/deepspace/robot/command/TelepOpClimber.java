package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.rambots4571.deepspace.robot.Robot.rightStick;

public class TelepOpClimber extends CommandBase {
    private Climber climber = Climber.getInstance();

    public TelepOpClimber() {
        addRequirements(climber);
    }

    @Override
    public void execute() {
        if (rightStick.getPOV() == 0) climber.setClimberMotor(0.5);
        else if (rightStick.getPOV() == 180) climber.setClimberMotor(-0.5);
        else climber.stopClimber();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
