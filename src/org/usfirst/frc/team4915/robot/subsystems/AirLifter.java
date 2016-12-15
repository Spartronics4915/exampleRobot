package org.usfirst.frc.team4915.robot.subsystems;

import org.usfirst.frc.team4915.robot.Robot;
import org.usfirst.frc.team4915.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class AirLifter extends Subsystem 
{    
    private DoubleSolenoid m_solenoid;
    private Robot m_robot;
    
    public AirLifter(Robot r)
    {
        m_robot = r;
        m_solenoid = new DoubleSolenoid(RobotMap.PCMCanID, 
                                  RobotMap.AirLifterSolenoidChannel,
                                  RobotMap.AirLifterSolenoid2Channel);
    }

    public void initDefaultCommand() 
    {
        // we don't need a default command yet
    }
    
    public void actuate(boolean onoff)
    {
        m_robot.logger.notice("AirLifter.actuate:" + (onoff ? "push" : "pull"));
        m_solenoid.set( onoff ? Value.kForward : Value.kReverse );
    }
    
    public Value get()
    {
        return m_solenoid.get();
    }
    
    public disable()
    {
        m_solenoid.set(Value.kOff);
    }
}

