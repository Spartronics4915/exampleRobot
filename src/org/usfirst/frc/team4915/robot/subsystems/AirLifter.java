package org.usfirst.frc.team4915.robot.subsystems;

import org.usfirst.frc.team4915.robot.Robot;
import org.usfirst.frc.team4915.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class AirLifter extends Subsystem 
{    
    private Solenoid m_solenoid; // single solenoid currently
    private Robot m_robot;
    
    public AirLifter(Robot r)
    {
        m_robot = r;
        m_solenoid = new Solenoid(RobotMap.PCMCanID, 
                                  RobotMap.AirLifterSolenoidChannel);
    }

    public void initDefaultCommand() {
        // we don't need a default command yet
    }
    
    public void actuate(boolean onoff)
    {
        m_robot.logger.notice("AirLifter.actuate:" + onoff);
        m_solenoid.set(onoff);
    }
    
    public boolean get()
    {
        return m_solenoid.get();
    }
}

