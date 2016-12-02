package org.usfirst.frc.team4915.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;
import org.usfirst.frc.team4915.robot.subsystems.Lifter;
import org.usfirst.frc.team4915.robot.RobotMap;

// WPI runtime assumes/requires we have a class called Robot
//
public class Robot extends IterativeRobot 
{
    public Logger logger = Logger.getInstance(); // runs during constructor
    
    private OI m_oi;
    
    // subsystems
    private AresDriveTrain m_driveTrain;
    private Lifter m_lifter;
    
    // misc sensors that aren't part of a subsystem
    private DigitalOutput m_photonicCannon;
    
    // internal state
    private Command m_autoCmd;
    
    // accessors
    public AresDriveTrain getDriveTrain() { return m_driveTrain; }
    public Lifter getLifter() { return m_lifter; }
    public DigitalOutput getPhotonicCannon() { return m_photonicCannon; }

    /** robotInit:
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        
        // first initialize subsystems
        m_driveTrain = new AresDriveTrain(this);
        m_lifter = new Lifter(this);
        
        // misc constructors
        m_photonicCannon = new DigitalOutput(RobotMap.photonicCannonPin);
       
        // LAST: operator interface; needs access to actuators/subsystems
        // for commands.
        m_oi = new OI(this);
        
        this.logger.notice("robotInit complete");
    }
    
    public void disabledInit()
    {
        this.logger.notice("disabled Init");
        // reset any subsystem information you want to clear when robot
        // is disabled.
   }
    
    public void disabledPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    /**
     * OI is responsible for defining the interface to autonomous.
     * At the moment we enter autonomous mode, we obtain the 
     * auto command selected by the driver.
     */
    public void autonomousInit() 
    {
        this.logger.notice("autonomousInit");
        m_autoCmd = m_oi.getAutoCmd();
        if(m_autoCmd != null)
        {
            m_autoCmd.start();
        }
    }

     public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
        this.logger.notice("teleopInit");
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

     public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
