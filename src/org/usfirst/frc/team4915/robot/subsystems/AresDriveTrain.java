
package org.usfirst.frc.team4915.robot.subsystems;

import org.usfirst.frc.team4915.robot.RobotMap;
import org.usfirst.frc.team4915.robot.Robot;
import org.usfirst.frc.team4915.robot.commands.DriveManualCmd;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;


public class AresDriveTrain extends Subsystem 
{
    // member variables ---------------------------------------------------
    private Robot m_robot;
    private RobotDrive m_robotDrive;
    
    private CANTalon m_leftMasterMotor;
    private CANTalon m_rightMasterMotor;
    private CANTalon m_leftFollowerMotor;
    private CANTalon m_rightFollowerMotor;
    
    private Joystick m_driveStick; // passed to us after constructor by OI
    
    // constants ----------------------------------------------------------
    private static final int quadTicksPerWheelRev = 9830;
    private static final double wheelDiameterInInches = 14.0;
    private static final double wheelCircumferenceInInches = wheelDiameterInInches * Math.PI;
    private static final double quadTicksPerInch = quadTicksPerWheelRev / wheelCircumferenceInInches;
    
    // methods ---------------------------------------------------
    public AresDriveTrain(Robot r)
    {
        // STEP 1: instantiate the motor controllers, we guard against
        // exceptions that might be caused if motors are missing, etc.
        m_robot = r;
        try
        {
            m_leftMasterMotor = new CANTalon(RobotMap.driveTrainLeftMasterMotorID);
            m_leftFollowerMotor = new CANTalon(RobotMap.driveTrainLeftFollowerMotorID);
            
            m_rightMasterMotor = new CANTalon(RobotMap.driveTrainRightMasterMotorID);
            m_rightFollowerMotor = new CANTalon(RobotMap.driveTrainRightFollowerMotorID);
    
            // Step 2: Configure the follower Talons: left & right back motors
            m_leftFollowerMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            m_leftFollowerMotor.set(m_leftMasterMotor.getDeviceID());
    
            m_rightFollowerMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
            m_rightFollowerMotor.set(m_rightMasterMotor.getDeviceID());
    
            // STEP 3: Setup speed control mode for the master Talons
            m_leftMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
            m_rightMasterMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
    
            // STEP 4: Indicate the feedback device used for closed-loop
            // For speed mode, indicate the ticks per revolution
            m_leftMasterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            m_rightMasterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            m_leftMasterMotor.configEncoderCodesPerRev(quadTicksPerWheelRev);
            m_rightMasterMotor.configEncoderCodesPerRev(quadTicksPerWheelRev);
    
            // STEP 5: Set PID values & closed loop error
            m_leftMasterMotor.setPID(0.22, 0, 0);
            m_rightMasterMotor.setPID(0.22, 0, 0);
    
            // Add ramp up rate
            m_leftMasterMotor.setVoltageRampRate(48.0); // max allowable voltage
                                                      // change /sec: reach to
                                                      // 12V after 1sec
            m_rightMasterMotor.setVoltageRampRate(48.0);
    
            
            m_robotDrive = new RobotDrive(m_leftMasterMotor, 
                                          m_rightMasterMotor);
            
            m_robot.logger.notice("AresDriveTrain initialized successfully!");
        }
        catch(Exception e)
        {
            m_robot.logger.exception(e, true /*no stack trace needed*/);
        }
    }
    
    public void setDriveStick(Joystick j)
    {
        m_driveStick = j; // must precede call to initDefaultCommand
                          // that call is made by Scheduler::run
    }
    
    public void initDashboard()
    {
        // Add SmartDashboard controls for testing
        // Add SmartDashboard live window
        LiveWindow.addActuator("Drive Train", "Left Master 10", m_leftMasterMotor);
        LiveWindow.addActuator("Drive Train", "Right Master 12", m_rightMasterMotor);
    }

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        // ManualDriveCmd will pass the joystick values to our
        //  manualDrive method.
        assert m_driveStick != null;
        setDefaultCommand(new DriveManualCmd(this, m_driveStick));
    }
    
    public void manualDriveBegin()
    {
    }
    
    public void manualDrive(double moveValue, double rotateValue)
    {
        m_robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void autoDriveBegin(double distanceInMeters)
    {
        // XXX: implementme!
    }
    
    public void autoDrive()
    {    
        // XXX: implementme!
    }

    public boolean autoReachedTarget()
    {
        // XXX: implementme!
        // query encoders to determine distance traveled.
        return true;
    }
    
    public void stop()
    {
        // XXX: implementme!        
    }

}

