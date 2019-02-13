package org.usfirst.frc.team4571.robot;

import com.rambots4571.rampage.joystick.DriveStick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private final SendableChooser<Command> autoChooser
            = new SendableChooser<>();
    private Command autoCommand;

    public static DriveStick leftStick =
            new DriveStick(Constants.Controllers.LEFT_STICK);
    public static DriveStick rightStick =
            new DriveStick(Constants.Controllers.RIGHT_STICK);

    public void robotInit() {
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        autoCommand = autoChooser.getSelected();
        if (autoCommand != null) {
            autoCommand.start();
        }
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}