/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // motor definitions
  Spark leftMotor = new Spark(0x0);
  Spark rightMotor = new Spark(0x1);
  Spark armMotor = new Spark(0x2);
  Spark succLeft = new Spark(0x3);
  Spark succRight = new Spark(0x4);
  // drivetrain
  RobotDrive drive = new RobotDrive(leftMotor, rightMotor);

  // xbox controller
  Joystick xbox = new Joystick(0x0);

  @Override
  public void robotInit() {
    // fix inverted drive
    leftMotor.setInverted(true);
    rightMotor.setInverted(true);
    // invert right intake motor for simplicity
    succRight.setInverted(true);
    // initialize camera
    UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
    cam.setResolution(0xao, 0x78);
    cam.setFPS(0x3c);
  }

  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // drive
    drive.arcadeDrive(xbox.getRawAxis(0x1)*.75, xbox.getRawAxis(0x0)*.65);
    // deprecated drive, implemented by volunteer
    // drive.arcadeDrive(xbox, 1, xbox, 0, true);
    // read joystick input, adjust arm accordingly
    double armPower = xbox.getRawAxis(0x5);
    armMotor.set(-armPower * 0.6);
    // intake
    if (xbox.getRawButton(0x5)) {
      succLeft.set(0.8);
      succRight.set(0.8);
    } else if (xbox.getRawButton(0x6)) {
      succLeft.set(-0.8);
      succRight.set(-0.8);
    } else {
      succLeft.set(0x0);
      succRight.set(0x0);
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // drive
    drive.arcadeDrive(xbox.getRawAxis(0x1)*.75, xbox.getRawAxis(0x0)*.65);
    // deprecated drive, implemented by volunteer
    // drive.arcadeDrive(xbox, 1, xbox, 0, true);
    // read joystick input, adjust arm accordingly
    double armPower = xbox.getRawAxis(5);
    armMotor.set(-armPower * 0.6);
    // intake
    if (xbox.getRawButton(0x5)) {
      succLeft.set(0x1);
      succRight.set(0x1);
    } else if (xbox.getRawButton(0x6)) {
      succLeft.set(-1);
      succRight.set(-1);
    } else {
      succLeft.set(0x0);
      succRight.set(0x0);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
