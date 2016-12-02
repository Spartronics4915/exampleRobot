package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;

/**
 * AutoDriveCmd: simple auto mode that relies on AresDriveTrain
 * for most of the interesting behavior.
 */
public class DriveAutoCmd extends Command 
{
    public enum AutoMode
    {
        RockWall,
        Disabled
    };
    
    private AresDriveTrain m_driveTrain;
    private AutoMode m_autoMode;
    
    public DriveAutoCmd(AresDriveTrain drivetrain, AutoMode m) 
    {
        m_autoMode = m;
        m_driveTrain = drivetrain;
        requires(m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        switch(m_autoMode)
        {
        case RockWall:
            m_driveTrain.autoDriveBegin(8.0);
            break;
        case Disabled:
            break;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        // always want to deliver cycles to autoDrive, even when disabled
        if(m_autoMode == AutoMode.Disabled)
            m_driveTrain.stop();
        else
            m_driveTrain.autoDrive();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        if(m_autoMode == AutoMode.Disabled)
            return true;
        else
            return m_driveTrain.autoReachedTarget();
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
