// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.WinchClimber;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchClimber extends SubsystemBase {
  private static final String TAB_NAME = "Winch Climber";

  private TalonFX winchMotor;

  private GenericEntry speedEntry = Shuffleboard.getTab(TAB_NAME).add("Winch Motor Percent", 0.0).getEntry();
  private GenericEntry winchToggleButton = Shuffleboard.getTab(TAB_NAME).add("Winch Toggle", false).getEntry();

  /** Creates a new WinchClimber. */
  public WinchClimber(int canId) {
    winchMotor = new TalonFX(canId);
    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    
    winchMotor.getConfigurator().apply(config);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(winchToggleButton.getBoolean(false)) {
      winchMotor.set(speedEntry.getDouble(0.0));
    } else {
      winchMotor.stopMotor();
    }
  }
}
