package com.rambots4571.deepspace.robot;

import com.rambots4571.deepspace.robot.command.teleop.TeleOpElevator;
import com.rambots4571.deepspace.robot.command.teleop.TeleOpIntake;
import com.rambots4571.rampage.joystick.DriveStick;
import com.rambots4571.rampage.joystick.Gamepad;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import static com.rambots4571.deepspace.robot.Constants.Controllers.*;

public class Robot extends TimedRobot {
    public static final DriveStick leftStick = new DriveStick(LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(RIGHT_STICK);
    public static final Gamepad gamepad = new Gamepad(GAMEPAD);

    @Override
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        teleopInit();
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().add(new TeleOpElevator());
        Scheduler.getInstance().add(new TeleOpIntake());
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
