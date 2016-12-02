package org.usfirst.frc.team4915.robot.commands;

import org.usfirst.frc.team4915.robot.Logger;
import org.usfirst.frc.team4915.robot.subsystems.Lifter;
import edu.wpi.first.wpilibj.command.Command;

/**
 * moves the lifter with no driver control. Optionally repeats.
 */
public class LifterAutoCtlCmd extends Command 
{
    private Lifter m_lifter;
    private boolean m_sweepFwd;
    private boolean m_repeat;
    private boolean m_done;
    
    public LifterAutoCtlCmd(Lifter l, boolean sweepFwd, boolean repeat)
    {
        m_lifter = l;
        m_sweepFwd = sweepFwd;
        m_repeat = repeat;
        requires(m_lifter);
    }

    protected void initialize()
    {
       Logger.getInstance().notice("LifterAutoCtlCmd initialize " +
                                   (m_sweepFwd ? "fwd " : "rev ") + 
                                   (m_repeat ? "repeating" : " once"));
       m_lifter.startMotor();
       m_done = false;
   }

    protected void execute() // (repeatedly)
    {
        if(m_sweepFwd)
        {
            if(m_lifter.getFwdLimitSwitch())
            {
               if(!m_repeat)
                   m_done = true;
               else
                   m_sweepFwd = false;
            }
            else
                m_lifter.runMotor(0.5); // constant forward velocity
        }
        else
        {
            if(m_lifter.getRevLimitSwitch())
            {
               if(!m_repeat)
                   m_done = true;
               else
                   m_sweepFwd = true;
            }
            else
                m_lifter.runMotor(-0.5); // constant reverse velocity

        }
        if(m_done)
            m_lifter.stopMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return m_done; // m_done s determined during execute
    }

    // Called once after isFinished returns true
    protected void end() 
    {
        m_lifter.stopMotor(); // defense against interrupts
        Logger.getInstance().notice("LifterAutoCtlCmd end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        Logger.getInstance().notice("LifterAutoCtlCmd interrupted");
    }
}
