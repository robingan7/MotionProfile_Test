/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveBase;
//import frc.robot.subsystems.Lift;
import frc.robot.OI;

//import frc.robot.subsystems.Lift;
//import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.NAVX;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.commands.auto.paths.StartFromRight.*;

public class Robot extends TimedRobot {
  public static OI m_oi;
  public static DriveBase drivebase;
  //public static Manipulator manipulator=new Manipulator();
  //public static Lift lift=new Lift();
  public static Compressor compressor;
  public static Joystick joy;
  public static Joystick joy2;
  public static NAVX navx;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    compressor=new Compressor();
    joy=new Joystick(0);
    joy2=new Joystick(1);
    drivebase=new DriveBase();
    navx=new NAVX();
    
    m_oi = new OI();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    new RightFirst().start();
    
  }
  
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    /*
    Scheduler.getInstance().run();
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(RobotMap.forward_backward), -1*Robot.joy.getRawAxis(RobotMap.turn));
    
    if(joy.getRawButton(2)) {
    drivebase.liftDB(true);
    }else{
    drivebase.liftDB(false);
  }

   if(Robot.joy.getRawButton(1)){
    Robot.drivebase.shiftGears(true);
  } else{
  Robot.drivebase.shiftGears(false);
  }
      if(Robot.joy.getRawButton(6)){
        Robot.manipulator.grabHatch(true);
      } else{
      Robot.manipulator.grabHatch(false);
      }

      if(Robot.joy2.getRawAxis(1)>.2||Robot.joy2.getRawAxis(1)<-0.2){
        lift.moveToPercent(joy2.getRawAxis(1)*.5);
      }else if (Robot.joy2.getRawAxis(1)<.2||Robot.joy2.getRawAxis(1)>-0.2){
        lift.moveToPercent(0);
      }
       
      if(Robot.joy2.getRawAxis(5)>.2||Robot.joy2.getRawAxis(5)<-0.2){
        manipulator.tiltWristPercent(joy2.getRawAxis(5)*.5);
      }else if (Robot.joy2.getRawAxis(5)<.2||Robot.joy2.getRawAxis(5)>-0.2){
        manipulator.tiltWristPercent(0);
      }*/
    }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(RobotMap.forward_backward), Robot.joy.getRawAxis(RobotMap.turn));
  }
}
