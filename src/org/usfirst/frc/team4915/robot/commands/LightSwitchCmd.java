package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LightSwitchCmd extends Command {
    private boolean m_enabled;
    private DigitalOutput m_lightswitch;
    
    public LightSwitchCmd(DigitalOutput lightswitch) 
    {
        // we are the sole command to control the light,
        // no subsystem requirements
        m_lightswitch = lightswitch;
        m_enabled = false;
        m_lightswitch.set(m_enabled); // make sure it's initially off
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        m_enabled = !m_enabled;
        m_lightswitch.set(m_enabled);
        System.out.println("Light is now " + m_enabled);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    }
}
