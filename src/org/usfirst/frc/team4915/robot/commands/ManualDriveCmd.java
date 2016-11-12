
package org.usfirst.frc.team4915.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4915.robot.subsystems.AresDriveTrain;
import org.usfirst.frc.team4915.robot.Robot;

/*
 * ManualDriveCmd: interfaces to AresDriveTrain to deliver
 * joystick values to its manualDrive method.
 * Currently we assert that we are never finished. We *can*
 * be interrupted.  ManualDriveCmd is probably the default
 * command associated with AresDriveTrain and so it is in
 * play whenever no other command requires AresDriveTrain.
 */
public class ManualDriveCmd extends Command 
{
    // member variables
    private AresDriveTrain m_driveTrain;
    private Joystick m_joystick;
    private double m_joystickX, m_joystickY;
    
    private final double m_turnMultiplier = -.55; 
    
    // methods
    public ManualDriveCmd(AresDriveTrain driveTrain, Joystick joystick) 
    {
        m_driveTrain = driveTrain;
        m_joystick = joystick;
        requires(m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        m_driveTrain.manualDriveBegin();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        m_joystickX = m_joystick.getAxis(Joystick.AxisType.kX);
        m_joystickY = m_joystick.getAxis(Joystick.AxisType.kY);
        double move = -m_joystickY;
        double rotate = m_joystickY * m_turnMultiplier;
        m_driveTrain.manualDrive(move, rotate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
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
