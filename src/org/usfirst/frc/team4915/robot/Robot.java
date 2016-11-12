package org.usfirst.frc.team4915.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;
import org.usfirst.frc.team4915.robot.RobotMap;


/**
 * The WPILib runtime is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot 
{
    private OI m_oi;
	
	// subsystems
    private AresDriveTrain m_driveTrain;
    
    // misc sensors that aren't part of a subsystem
    private DigitalOutput m_photonicCannon;
    
    // internal state
    private Command m_autoCmd;
    
    // accessors
    public OI getOI() { return m_oi; }
    public AresDriveTrain getDriveTrain() { return m_driveTrain; }
    public DigitalOutput getPhotonicCannon() { return m_photonicCannon; }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        // first initialize subsystems
		m_driveTrain = new AresDriveTrain(m_oi); 
		m_photonicCannon = new DigitalOutput(RobotMap.photonicCannonPin);
       
		// last initialize operator interface; needs access to actuators
		// for commands.
		m_oi = new OI(this);
		
		Logger.getInstance().logInfo("robotInit complete");
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit()
    {
    }
	
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}

	/**
     * OI is reponsible for defining the interface to autonomous.
     * At the moment we enter autonomous mode, we obtain the 
     * auto command selected by the driver.
	 */
    public void autonomousInit() 
    {
        m_autoCmd = m_oi.getAutoCmd();
        if(m_autoCmd != null)
        {
            m_autoCmd.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if(m_autoCmd != null)
        {
            m_autoCmd.cancel();
        }
        // at this stage we expect the scheduler to activate the
        // default commands for each subsystem.
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
