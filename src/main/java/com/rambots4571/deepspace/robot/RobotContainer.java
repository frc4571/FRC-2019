package com.rambots4571.deepspace.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.rambots4571.deepspace.robot.subsystem.Drivetrain;
import com.rambots4571.rampage.joystick.DriveStick;
import com.rambots4571.rampage.joystick.Gamepad;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.util.List;

import static com.rambots4571.deepspace.robot.Constants.Controllers.*;

public class RobotContainer {
    public static final DriveStick leftStick = new DriveStick(LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(RIGHT_STICK);
    public static final Gamepad gamepad = new Gamepad(GAMEPAD);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {}

    public Command followTrajectory(Trajectory trajectory) {
        Drivetrain.getInstance().setNeutralMode(NeutralMode.Brake);

        RamseteCommand ramseteCommand = new RamseteCommand(
                trajectory,
                Drivetrain.getInstance()::getPose,
                new RamseteController(
                        Constants.Drive.kRamseteB,
                        Constants.Drive.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.Drive.ksVolts,
                        Constants.Drive.kvVoltsSecPerMeter,
                        Constants.Drive.kaVoltSecSquaredPerMeter),
                Drivetrain.getInstance().getKinematics(),
                Drivetrain.getInstance()::getSpeeds,
                new PIDController(Constants.Drive.kP, 0.0, Constants.Drive.kD),
                new PIDController(Constants.Drive.kP, 0.0, Constants.Drive.kD),
                Drivetrain.getInstance()::setVoltage,
                Drivetrain.getInstance()
        );
        return ramseteCommand.andThen(() -> Drivetrain.getInstance().stop());
    }

    public Command testTraj() {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                List.of(new Pose2d(),
                        new Pose2d(2, 0, new Rotation2d())), Drivetrain.getInstance().getTrajectoryConfig());
        return followTrajectory(trajectory);
    }
}
