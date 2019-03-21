package com.rambots4571.deepspace.robot;

import com.rambots4571.deepspace.robot.command.teleop.TeleOpElevator;
import com.rambots4571.deepspace.robot.command.teleop.TeleOpIntake;
import com.rambots4571.rampage.joystick.DriveStick;
import com.rambots4571.rampage.joystick.Gamepad;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
    public static final DriveStick leftStick = new DriveStick(
            Constants.Controllers.LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(
            Constants.Controllers.RIGHT_STICK);
    public static final Gamepad gamepad = new Gamepad(
            Constants.Controllers.GAMEPAD);

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
        teleopInit();
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
