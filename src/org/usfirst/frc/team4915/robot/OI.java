package org.usfirst.frc.team4915.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.usfirst.frc.team4915.robot.commands.ManualDriveCmd;
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
    
    private Joystick m_driveStick;
    private JoystickButton m_lightSwitchButton;
    private Robot m_robot;
   
    // constants
    private final int driveStickPort = 0;
    private final int lightSwitchButtonNum = 5;
    
    public OI(Robot robot)
    {
        m_robot = robot;
        
        initDriveOI();
       
        createButton(this.m_lightSwitchButton, m_driveStick, 
                     lightSwitchButtonNum, new LightSwitchCmd(m_robot.m_photonicCannon));
 
        initDashboard();
    }
    
    public Joystick getDriveStick() { return m_driveStick; }
    
    private void createButton(JoystickButton button, Joystick js, 
                              int buttonNumber, Command cmd) 
    {
        button = new JoystickButton(js, buttonNumber);
        button.whenPressed(cmd);
    }
 
    private void initDriveOI()
    {
        m_driveStick = new Joystick(driveStickPort);        
    }
    
    private void initDashboard()
    {
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
            System.out.println("Build " + buildStr);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
       
    }
}

