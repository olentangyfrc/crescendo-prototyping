// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.DualFlywheelShooter;

import java.util.Map;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DualFlywheelShooter extends SubsystemBase {
  public static final String TAB_NAME = "Dual Flywheel Shooter";

  private GenericEntry leftRatioEntry = Shuffleboard.getTab(TAB_NAME)
    .add("Left Ratio", 0.0)
    .getEntry();
  private GenericEntry rightRatioEntry = Shuffleboard.getTab(TAB_NAME)
    .add("Right Ratio", 0.0)
    .getEntry();
  
  private GenericEntry speedEntry = Shuffleboard.getTab(TAB_NAME)
    .add("Speed", 0.0)
    .withProperties(Map.of("min", -1.0, "max", 1.0))
    .getEntry();


  private GenericEntry shooterToggleButton = Shuffleboard.getTab(TAB_NAME)
    .add("Shooter On", false)
    .withWidget(BuiltInWidgets.kToggleButton)
    .getEntry();

  private TalonFX leftMotor;
  private TalonFX rightMotor;

  /** Creates a new OneSidedShooter. */
  public DualFlywheelShooter(int leftCanId, int rightCanId) {
    leftMotor = new TalonFX(leftCanId);
    rightMotor = new TalonFX(rightCanId);

    TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

    leftMotor.getConfigurator().apply(motorConfig);
    rightMotor.getConfigurator().apply(motorConfig);

    rightMotor.setInverted(true);

    Shuffleboard.getTab(TAB_NAME).addNumber("Left applied", leftMotor::get);
    Shuffleboard.getTab(TAB_NAME).addNumber("Right applied", rightMotor::get);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(shooterToggleButton.getBoolean(false)) {
      leftMotor.set(speedEntry.getDouble(0.0) * leftRatioEntry.getDouble(0.0) / rightRatioEntry.getDouble(1));
      rightMotor.set(speedEntry.getDouble(0.0));
    } else {
      leftMotor.stopMotor();
      rightMotor.stopMotor();
    }
  }
}
