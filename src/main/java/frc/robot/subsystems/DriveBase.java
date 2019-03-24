/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public WPI_TalonSRX frontRight, frontLeft;
  public WPI_VictorSPX backRight, backLeft, midRight, midLeft;
  //private double leftOutput, rightOutput;
  //private Solenoid shifterSolenoid;
  private Solenoid dbLifter;
  private Solenoid shifter;
  private boolean solenoidPos;
  private int direction=1;
  private boolean gearB;
  //private double goal;
  private DifferentialDrive m_Drive;
   public DriveBase(){
     dbLifter= new Solenoid(RobotMap.drivebaselifter);
     shifter = new Solenoid(4);
     
    solenoidPos=false;
    frontRight = new WPI_TalonSRX(RobotMap.dbFrontRight);
    midRight = new WPI_VictorSPX(RobotMap.dbMidRight);
    backRight = new WPI_VictorSPX(RobotMap.dbBackRight);
      midRight.follow(frontRight);
      backRight.follow(frontRight);
    
    frontLeft = new WPI_TalonSRX(RobotMap.dbFrontLeft);
    midLeft = new WPI_VictorSPX(RobotMap.dbMidLeft);  
    backLeft = new WPI_VictorSPX(RobotMap.dbBackLeft);
      midLeft.follow(frontLeft);
      backLeft.follow(frontLeft);
    
      SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRight, midRight, backRight);
      SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeft, midLeft, backLeft);

      m_Drive = new DifferentialDrive(m_Left, m_Right);

  }

  public void arcadeDrive(double forward, double rotate){
    m_Drive.arcadeDrive(forward*direction, 0.75*rotate);
  }
  public void visionDrive(double xSpeed, double zRotation) {
    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

    if (xSpeed >= 0.0) {
      
      if (zRotation >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      } else {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      }
    } else {
      
      if (zRotation >= 0.0) {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      }
    }

    frontLeft.set(leftMotorOutput);
    frontRight.set(rightMotorOutput * -1);
  }
  public void switchDirection(){
    direction = direction*-1;
  }

  public void driveDistance(double g){
    //frontLeft.set(ControlMode.MotionMagic, g);
    //frontRight.set(ControlMode.MotionMagic, g);
  }

  public void liftDB(boolean state){
    solenoidPos = state;
  }
  public boolean getLiftDB(){
    return solenoidPos;
  }
  public void shiftGears(boolean s){
    gearB=s;
  }
  public SensorCollection[] getEncoders()
	{
		return new SensorCollection[] {this.frontLeft.getSensorCollection(), this.frontRight.getSensorCollection()};
	}
  @Override
  public void periodic() {
    dbLifter.set(solenoidPos);
    shifter.set(gearB);
    //super.periodic();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new MySpecialCommand());
  }
}
