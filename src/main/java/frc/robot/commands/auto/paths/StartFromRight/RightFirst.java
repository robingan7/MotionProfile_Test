package frc.robot.commands.auto.paths.StartFromRight;

import java.io.File;
import java.io.IOException;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class RightFirst extends Command
{

    Trajectory trajectory = null;
    TankModifier modifier = null;
    EncoderFollower left = null;
    EncoderFollower right = null;
    // File myFile = null;
    int leftEncoder, rightEncoder;
    File myFileLeft,myFileRight;
   
    double gyroHeading, l, r, desiredHeading, angleDifference, turn;
    public static final String pathname="/home/lvuser/paths/";
	public RightFirst()
	{
        this.requires(Robot.drivebase);
        this.requires(Robot.navx);

        myFileLeft = new File(pathname+"rightone.left.pf1.csv");
        myFileRight = new File(pathname+"rightone.right.pf1.csv");
        Trajectory leftTrajectory,rightTrajectory;
        try{
            leftTrajectory = Pathfinder.readFromCSV(myFileLeft);
            //Trajectory leftTrajectory = PathfinderFRC.getTrajectoryFile(pathname);
            rightTrajectory = Pathfinder.readFromCSV(myFileRight);
            left = new EncoderFollower(leftTrajectory);
            right = new EncoderFollower(rightTrajectory);
        }
        catch(Exception IOException){
            System.out.println("Can't find file");
        }
        
        left.configureEncoder(Math.abs(Robot.drivebase.frontLeft.getSensorCollection().getQuadraturePosition()), 4096, 0.15);
        right.configureEncoder(Math.abs(Robot.drivebase.frontRight.getSensorCollection().getQuadraturePosition()), 4096, 0.15);
        left.configurePIDVA(1.2, 0.00, 0.00, 1.0 / 5.107479538, 0);
        right.configurePIDVA(1.2, 0.00, 0.00, 1.0 / 5.107479538, 0);

        // myFile = new File("myfile.csv");
        // Pathfinder.writeToCSV(myFile, trajectory);

        this.setTimeout(1000);
    }
	@Override
	protected void end()
	{

    }
	@Override
	protected void execute()
	{

        leftEncoder = Robot.drivebase.frontLeft.getSensorCollection().getQuadraturePosition();
        SmartDashboard.putNumber("left_encoder", leftEncoder);
        rightEncoder = Robot.drivebase.frontRight.getSensorCollection().getQuadraturePosition();
        SmartDashboard.putNumber("right endocer", rightEncoder);
        l = left.calculate(leftEncoder);
        r = right.calculate(rightEncoder);
        SmartDashboard.putNumber("left_calc", l);
        SmartDashboard.putNumber("right_calc", r);

        gyroHeading = Robot.navx.ahrs.getYaw();
        SmartDashboard.putNumber("heading", gyroHeading);
        desiredHeading = Pathfinder.r2d(left.getHeading());
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn = 0.8 * (-1.0/80.0) * angleDifference;

        Robot.drivebase.visionDrive(l + turn, r - turn);

        // if(l - turn > 0.55) {
        //     l = 0.5(l);
        //     turn = 0.5(turn);
        // }
    
    }
	@Override
	protected boolean isFinished()
	{
		return this.isTimedOut();
	}
}