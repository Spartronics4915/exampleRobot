package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.robot.Logger;
import org.usfirst.frc.team4915.robot.subsystems.Lifter;

public class LifterCheckLimitSwitchCmd extends Command
{
    private Lifter m_lifter;
    private boolean[] m_lastStates;
    
    public LifterCheckLimitSwitchCmd(Lifter l) 
    {
        m_lifter = l;
        m_lastStates = new boolean[3]; // array element for each switch
        // requires(m_lifter); // not strictly needed, since we don't change
                               // lifter's state.
    }

    protected void initialize() 
    {
        Logger.getInstance().notice("LifterCheckLimitSwitchCmd initialize");
        getSwitchValues();
        logValues();
    }

    protected void execute() 
    {
        if(getSwitchValues())
        {
            logValues();
        }
    }
    
    private void logValues()
    {
        Logger.getInstance().notice("LifterLimitSwitches:" +
                " FWD:" + m_lastStates[0] +
                " REV:" + m_lastStates[1] +
                " DIO:" + m_lastStates[2]);
    }
    
    protected boolean isFinished()
    {
        return false;
    }

    protected void end() 
    {
        Logger.getInstance().notice("LifterCheckLimitSwitchCmd end");
    }

    protected void interrupted() 
    {
        Logger.getInstance().notice("LifterCheckLimitSwitchCmd interrupted");
    }
 
    private boolean getSwitchValues()
    {
        boolean val, stateChanged=false;
        val = m_lifter.getFwdLimitSwitch();
        if(val != m_lastStates[0])
        {
            stateChanged = true;
            m_lastStates[0] = val;
        }
        val = m_lifter.getRevLimitSwitch();
        if(val != m_lastStates[1])
        {
            stateChanged = true;
            m_lastStates[1] = val;
        }
        val = m_lifter.getDIOLimitSwitch();
        if(val != m_lastStates[2])
        {
            stateChanged = true;
            m_lastStates[2] = val;
        }
        return stateChanged;
    }
}
