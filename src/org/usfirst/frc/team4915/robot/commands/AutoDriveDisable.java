package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;

/**
 *
 */
public class AutoDriveDisable extends Command {
    private AresDriveTrain m_driveTrain;
    
    public AutoDriveDisable(AresDriveTrain driveTrain) 
    {
        m_driveTrain = driveTrain;
        requires(m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        m_driveTrain.stop();
    }

    protected void execute() 
    {
    }

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end()
    {
    }

    protected void interrupted()
    {
    }
}
