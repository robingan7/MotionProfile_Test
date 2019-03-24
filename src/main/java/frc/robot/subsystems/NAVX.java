package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NAVX extends Subsystem
{
	private final SensorCollection[] ENCODERS = Robot.drivebase.getEncoders();
	public final AHRS ahrs = new AHRS(SPI.Port.kMXP);//(byte)200
	private double right_rotations, left_rotations, right_prev, right_vel, right_current, left_prev, left_current, left_vel, acc;
	private final PowerDistributionPanel PDP = new PowerDistributionPanel();
	
	public NAVX()
	{
		super();
		while(this.ahrs.isCalibrating())
		{
			; // Calibrating NavX
		}
		this.right_rotations = 0;
		this.left_rotations = 0;
		this.ahrs.reset();
		this.ahrs.zeroYaw();
		this.resetEncoders();
	}
	public void resetEncoders()
	{
		this.ENCODERS[0].setQuadraturePosition(0, 0);
		this.ENCODERS[1].setQuadraturePosition(0, 0);
		this.right_prev = 0;
		this.left_prev = 0;
		this.right_current = 0;
		this.left_current = 0;
	}
	private void updateEncoders()
	{
		//this.timePrev = this.time;
		//this.time = System.currentTimeMillis();
		this.right_prev = this.right_current;
		//this.left_velPREV = this.right_vel;
		this.right_current = Math.abs(this.ENCODERS[0].getQuadraturePosition());
		this.right_vel = Math.abs(this.ENCODERS[0].getQuadratureVelocity());
		this.acc = Math.abs(this.ahrs.getWorldLinearAccelX());
		this.left_prev = this.left_current;
		//this.left_velPREV = this.left_prev;
		this.left_current = Math.abs(this.ENCODERS[1].getQuadraturePosition());
		this.left_vel = Math.abs(this.ENCODERS[1].getQuadratureVelocity());
	}
	public double[] distances()
	{
		this.updateEncoders();
		this.right_rotations = (Math.abs(this.right_current) - Math.abs(this.right_prev)) / 4096.0; // 12 bit data
		this.left_rotations = (Math.abs(this.left_current) - Math.abs(this.left_prev)) / 4096.0; // 12 bit data
		return new double[] {Math.abs(this.left_rotations), Math.abs(this.right_rotations)}; // Both inches measurements
	}

	public double[] velocity() 
	{
		this.updateEncoders();
		return new double[] {Math.abs(this.left_vel), Math.abs(this.right_vel)};
	}

	public double acc() {
		this.updateEncoders();
		return this.acc; 
	}
	
	public void displayVoltage()
	{
		SmartDashboard.putNumber("System Voltage", this.PDP.getVoltage());
	}
    
    @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new MySpecialCommand());
  }
	
}