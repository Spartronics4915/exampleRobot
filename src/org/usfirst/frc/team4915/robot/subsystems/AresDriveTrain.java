
package org.usfirst.frc.team4915.robot.subsystems;

import org.usfirst.frc.team4915.robot.OI;
import org.usfirst.frc.team4915.robot.RobotMap;
import org.usfirst.frc.team4915.robot.commands.ManualDriveCmd;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;


public class AresDriveTrain extends Subsystem 
{
    // member variables ---------------------------------------------------
    private OI m_oi;
    private RobotDrive m_robotDrive;
    
    private CANTalon m_leftMasterMotor;
    private CANTalon m_rightMasterMotor;
    private CANTalon m_leftFollowerMotor;
    private CANTalon m_rightFollowerMotor;
    
    // constants ----------------------------------------------------------
    private static final int quadTicksPerWheelRev = 9830;
    private static final double wheelDiameterInInches = 14.0;
    private static final double wheelCircumferenceInInches = wheelDiameterInInches * Math.PI;
    private static final double quadTicksPerInch = quadTicksPerWheelRev / wheelCircumferenceInInches;
    
    // methods ---------------------------------------------------
    public AresDriveTrain(OI oi)
    {
        m_oi = oi;
        
        // STEP 1: instantiate the motor controllers, we guard against
        // exceptions that might be caused if motors are missing, etc.
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
            
            System.out.println("AresDriveTrain initialized successfully!");
        }
        catch(Exception e)
        {
            System.out.println("WARNING AresDriveTrain initialized FAILED");
            e.printStackTrace();
        }
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
        setDefaultCommand(new ManualDriveCmd(this, m_oi.getDriveStick()));
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

