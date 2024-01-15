// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.OneSidedShooter;

import java.util.Map;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OneSidedShooter extends SubsystemBase {
  public static final String TAB_NAME = "One-sided Shooter";

  private GenericEntry speedEntry = Shuffleboard.getTab(TAB_NAME)
    .add("Shooter Percent", 0.0)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", -1.0, "max", 1.0))
    .getEntry();

  private TalonFX shooterMotor;

  public InstantCommand shooterOn;
  public InstantCommand shooterOff;

  /** Creates a new OneSidedShooter. */
  public OneSidedShooter(int CanId) {
    shooterMotor = new TalonFX(10);

    shooterOn = new InstantCommand() {
      @Override
      public void initialize() {
        shooterMotor.set(speedEntry.getDouble(0.0));
      }
    };

    shooterOff = new InstantCommand() {
      @Override
      public void initialize() {
        shooterMotor.stopMotor();
      }
    };

    Shuffleboard.getTab(TAB_NAME).add("Turn On Shooter", shooterOn);
    Shuffleboard.getTab(TAB_NAME).add("Turn Off Shooter", shooterOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
