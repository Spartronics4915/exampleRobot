package org.usfirst.frc.team4915.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * NB: NO CODE HERE!  ONLY CONSTANTS.
 */
public class RobotMap 
{
    // CAN Devices
    public static final int PCMCanID = 0; 
    public static final int lifterMotorID = 10;
    public static final int driveTrainLeftMasterMotorID = 11;
    public static final int driveTrainLeftFollowerMotorID = 12;
    public static final int driveTrainRightMasterMotorID = 13;
    public static final int driveTrainRightFollowerMotorID = 14;
    
    // DigitalInput/Output (DIO) pins
    public static int lifterDIOSwitchPin = 0;
    public static int photonicCannonPin = 1;
    
    // PWM Controls
    public static final int AirLifterSolenoidChannel = 0;
    public static final int AirLifterSolenoid2Channel = 1;
     
    // SPI Devices
    
    // I2C Devices
    
    // Analog Inputs
    
    // Relay Controls
}
