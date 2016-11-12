package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;

/**
 *
 */
public class AutoDriveRockwall extends Command {
    private AresDriveTrain m_driveTrain;
    
    public AutoDriveRockwall(AresDriveTrain drivetrain) 
    {
        m_driveTrain = drivetrain;
        requires(m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        m_driveTrain.autoDriveBegin(8.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        m_driveTrain.autoDrive();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
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
