package com.rambots4571.deepspace.robot;

import com.rambots4571.deepspace.robot.command.TeleOpDrive;
import com.rambots4571.deepspace.robot.command.TeleOpElevator;
import com.rambots4571.deepspace.robot.command.TeleOpIntake;
import com.rambots4571.rampage.joystick.DriveStick;
import com.rambots4571.rampage.joystick.Gamepad;
import com.rambots4571.rampage.vision.CamMode;
import com.rambots4571.rampage.vision.LedMode;
import com.rambots4571.rampage.vision.Limelight;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import static com.rambots4571.deepspace.robot.Constants.Controllers.*;

public class Robot extends TimedRobot {
    public static final DriveStick leftStick = new DriveStick(LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(RIGHT_STICK);
    public static final Gamepad gamepad = new Gamepad(GAMEPAD);

    @Override
    public void robotInit() {
//        Limelight.setCameraMode(CamMode.DriverCamera);
//        Limelight.setLedMode(LedMode.Off);
    }

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
        Scheduler.getInstance().add(new TeleOpDrive());
        Scheduler.getInstance().add(new TeleOpElevator());
        Scheduler.getInstance().add(new TeleOpIntake());
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
