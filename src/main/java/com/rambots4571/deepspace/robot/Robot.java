package com.rambots4571.deepspace.robot;

import com.rambots4571.deepspace.robot.command.autonomous.FollowPath;
import com.rambots4571.deepspace.robot.command.autonomous.TurnCommand;
import com.rambots4571.deepspace.robot.command.teleop.TeleOpElevator;
import com.rambots4571.deepspace.robot.command.teleop.TeleOpIntake;
import com.rambots4571.rampage.joystick.DriveStick;
import com.rambots4571.rampage.joystick.Gamepad;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    public static final DriveStick leftStick = new DriveStick(
            Constants.Controllers.LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(
            Constants.Controllers.RIGHT_STICK);
    public static final Gamepad gamepad = new Gamepad(
            Constants.Controllers.GAMEPAD);

    private final SendableChooser<Command> autoChooser
            = new SendableChooser<>();
    private Command autoCommand;

    public void robotInit() {
        autoChooser.addOption("run test path", new FollowPath("testpath"));
        autoChooser.addOption("Turn 90 Degrees", new TurnCommand(90.0D));
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
        Scheduler.getInstance().add(new TeleOpElevator());
        Scheduler.getInstance().add(new TeleOpIntake());
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
