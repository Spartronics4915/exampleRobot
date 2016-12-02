package org.usfirst.frc.team4915.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team4915.robot.Robot;
import org.usfirst.frc.team4915.robot.RobotMap;
import org.usfirst.frc.team4915.robot.commands.LifterManualCtlCmd;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;

public class Lifter extends Subsystem 
{
    private Robot m_robot;
    private CANTalon m_mainMotor;
    private Joystick m_liftStick;
    private DigitalInput m_dioLimitSwitch; // an extra limit switch
    private boolean m_motorIsActive;
    
    private final double k_minJoystickValue = .1;
    
    public Lifter(Robot r)
    {
        m_robot = r;
        
        try
        {
            m_mainMotor = new CANTalon(RobotMap.lifterMotorID);
            m_mainMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
            m_mainMotor.configPeakOutputVoltage(12.0, -12.0);
            m_mainMotor.enableBrakeMode(true);
        // for now we'll manually check limit switches
            // m_mainMotor.enableLimitSwitch(true, true);
        }
        catch(Exception e)
        {
            m_robot.logger.error("Lifter.m_mainMotor initialization failure");
            m_robot.logger.exception(e, true /*no stack trace needed*/);
        }
        
        m_dioLimitSwitch = new DigitalInput(RobotMap.lifterDIOSwitchPin);
        m_motorIsActive = false;
        m_robot.logger.notice("Lifter initialized");
    }
    public void setLifterStick(Joystick j)
    {
        m_liftStick = j;
    }
    public void initDefaultCommand()
    {
        setDefaultCommand(new LifterManualCtlCmd(this));
    }
    public void initDashboard()
    {
        LiveWindow.addActuator("Lifter", "main motor", m_mainMotor);
        // TODO: add SmartDashboard status
    }
    // limit switches -------------------------------------------------
    public boolean getFwdLimitSwitch()
    {
        return m_mainMotor.isFwdLimitSwitchClosed();
    }
    public boolean getRevLimitSwitch() 
    {
        return m_mainMotor.isRevLimitSwitchClosed();
    }
    public boolean getDIOLimitSwitch() 
    {
        return m_dioLimitSwitch.get();
    }
    public boolean isAnyLimitSwitchClosed()
    {
        return getFwdLimitSwitch() || getRevLimitSwitch() || getDIOLimitSwitch();
    }
    // motor controls -------------------------------------------------
    public void manualCtl()
    {
        double y = m_liftStick.getY(GenericHID.Hand.kLeft); // NB: hand shouldn't matter for our joystick
        m_robot.logger.debug("manualCtl:" + y);
        if(isAnyLimitSwitchClosed() || 
           Math.abs(y) < k_minJoystickValue)
        {
            stopMotor();
        }
        else
        {
            startMotor();
            m_mainMotor.set(y); // Set accepts values between -1 and 1
                                // It's common to remap these values to
                                // give the drive more control.
        }
    }
    
    public void runMotor(double speed)
    {
        if(m_motorIsActive)
            m_mainMotor.set(speed);
        else
            m_robot.logger.warning("lifter:runMotor called when motor is inactive");
    }
    
    public void stopMotor()
    {
        if(m_motorIsActive)
        {
            m_mainMotor.set(0);
            m_mainMotor.disableControl();
            m_motorIsActive = false;
        }
    }
    
    public void startMotor()
    {
        if(!m_motorIsActive)
        {
            m_mainMotor.enableControl();
            m_motorIsActive = true;
        }
    }
}

