package com.rambots4571.deepspace.robot;

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
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
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

    public Command getTrajectoryCommand() {
        DifferentialDriveVoltageConstraint autoVoltageConstraint
                = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                        Constants.Drive.ksVolts,
                        Constants.Drive.kvVoltsSecPerMeter,
                        Constants.Drive.kaVoltSecSquaredPerMeter),
                Drivetrain.getInstance().getKinematics(), 10);

        TrajectoryConfig config = new TrajectoryConfig(
                Units.feetToMeters(Constants.Drive.maxVel),
                Units.feetToMeters(Constants.Drive.maxAccel)).setKinematics(
                Drivetrain.getInstance().getKinematics()).addConstraint(
                autoVoltageConstraint);

        Trajectory example = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(
                        new Translation2d(1, 1),
                        new Translation2d(2, -1)),
                new Pose2d(3, 0, new Rotation2d(0)), config);

        RamseteCommand ramseteCommand = new RamseteCommand(
                example,
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
                new PIDController(Constants.Drive.kP, 0.0, 0.0),
                new PIDController(Constants.Drive.kP, 0.0, 0.0),
                Drivetrain.getInstance()::setVoltage,
                Drivetrain.getInstance()
        );
        return ramseteCommand.andThen(() -> Drivetrain.getInstance().stop());
    }
}
