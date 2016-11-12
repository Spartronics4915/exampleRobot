package org.usfirst.frc.team4915.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.usfirst.frc.team4915.robot.commands.ManualDriveCmd;
import org.usfirst.frc.team4915.robot.commands.AutoDriveCmd;
import org.usfirst.frc.team4915.robot.commands.LightSwitchCmd;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    private Robot m_robot;
    private Joystick m_driveStick;
    private JoystickButton m_lightSwitchButton;
    private SendableChooser m_autoChooser;
    
    // constants
    private final int k_driveStickPort = 0;
    private final int k_lightSwitchButtonNum = 5;
    
    public OI(Robot robot)
    {
        m_robot = robot;
        
        initDriveOI();
        initDashboard();
       
        createButton(this.m_lightSwitchButton, m_driveStick, 
                     k_lightSwitchButtonNum, 
                     new LightSwitchCmd(m_robot.getPhotonicCannon()));
 
    }
    
    public Joystick getDriveStick() { return m_driveStick; }
    
    private void initDriveOI()
    {
        m_driveStick = new Joystick(k_driveStickPort);        
    }
    
    private void initDashboard()
    {
        initAutoOI();
        m_robot.getDriveTrain().initDashboard();
        
        /*
         * VERSION STRING!!
         */
        try (InputStream manifest = getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) 
        {
            Attributes attributes = new Manifest(manifest).getMainAttributes();
            String buildStr = "by: " + attributes.getValue("Built-By") +
                              "  on: " + attributes.getValue("Built-At") +
                              "  vers:" + attributes.getValue("Code-Version");
            /* we'd like a single field on the smart dashboard for easier layout/tracking */
            SmartDashboard.putString("Build", buildStr);
            
            Logger.getInstance().logNotice("Build " + buildStr);;
        }
        catch (IOException e) 
        {
            Logger.getInstance().logException(e);
        }
    }

    private void initAutoOI()
    {
        m_autoChooser = new SendableChooser();
        m_autoChooser.addDefault("Rock Wall", new AutoDriveCmd(m_robot.getDriveTrain(), AutoDriveCmd.AutoMode.RockWall));
        m_autoChooser.addDefault("Disabled", new AutoDriveCmd(m_robot.getDriveTrain(), AutoDriveCmd.AutoMode.Disabled));
        SmartDashboard.putData("AutoMode", m_autoChooser);
    }
    
    public Command getAutoCmd()
    {
        return (Command) m_autoChooser.getSelected();
    }
    
    
    private void createButton(JoystickButton button, Joystick js, 
            int buttonNumber, Command cmd) 
    {
        button = new JoystickButton(js, buttonNumber);
        button.whenPressed(cmd);
    }
}

