package org.usfirst.frc.team4915.robot.commands;

import org.usfirst.frc.team4915.robot.Logger;
import org.usfirst.frc.team4915.robot.subsystems.AirLifter;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AirLifterCmd extends Command {

    AirLifter m_airLifter;
    
    public AirLifterCmd(AirLifter l)
    {
        m_airLifter = l;
        requires(m_airLifter);
    }

    protected void initialize() 
    {
    }
    
    public void start() // called repeatedly whileHeld
    {
        super.start(); // make sure superclass start is invoked
        if(!m_airLifter.get())
        {
            m_airLifter.actuate(true);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        // nothing to do, we did it during start
    }

    protected boolean isFinished() 
    {
        return true; // we're a one shot, but since "whenHeld" restarts repeatedly
                     // we expect multiple calls to isFinished.
    }

    protected void end() 
    {
        m_airLifter.actuate(false);
    }

    protected void interrupted() 
    {
        Logger.getInstance().warning("AirLifterCmd unexpectedly interrupted");
    }
}
