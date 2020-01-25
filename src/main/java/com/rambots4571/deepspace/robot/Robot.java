package com.rambots4571.deepspace.robot;

import com.rambots4571.deepspace.robot.command.TeleOpDrive;
import com.rambots4571.deepspace.robot.command.TeleOpElevator;
import com.rambots4571.deepspace.robot.command.TeleOpIntake;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.rampage.vision.CamMode;
import com.rambots4571.rampage.vision.LedMode;
import com.rambots4571.rampage.vision.Limelight;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {


    @Override
    public void robotInit() {
        Limelight.setLedMode(LedMode.Off);
        Limelight.setCameraMode(CamMode.DriverCamera);
        SmartDashboard.putData(Drivetrain.getInstance());
        SmartDashboard.putData(Drivetrain.getInstance());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
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
        (new TeleOpDrive()).schedule();
        (new TeleOpElevator()).schedule();
        (new TeleOpIntake()).schedule();
    }

    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
