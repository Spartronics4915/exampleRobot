package org.usfirst.frc.team4915.robot.commands;

import org.usfirst.frc.team4915.robot.Logger;
import org.usfirst.frc.team4915.robot.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

/**
 * uses joystick to control lifter motor 
 */
public class LifterManualCtlCmd extends Command 
{
    private Lifter m_lifter;
    
    public LifterManualCtlCmd(Lifter l)
    {
        m_lifter = l;
        requires(m_lifter);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
       Logger.getInstance().notice("LifterManualCtlCmd initialize");
   }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        m_lifter.manualCtl();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        Logger.getInstance().notice("LifterManualCtlCmd end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        Logger.getInstance().notice("LifterManualCmd interrupted");
    }
}
