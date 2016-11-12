package org.usfirst.frc.team4915.robot;
import org.usfirst.frc.team4915.robot.commands.ManualDriveCmd;
import org.usfirst.frc.team4915.robot.commands.AutoDriveCmd;
import org.usfirst.frc.team4915.robot.commands.LightSwitchCmd;

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


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
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
        try (InputStream manifest = getClass().getClassLoader().
                                        getResourceAsStream("META-INF/MANIFEST.MF")) 
        {
            // build a version string
            Attributes attributes = new Manifest(manifest).getMainAttributes();
            String buildStr = "by: " + attributes.getValue("Built-By") +
                              "  on: " + attributes.getValue("Built-At") +
                              "  vers:" + attributes.getValue("Code-Version");
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
        m_autoChooser.addDefault("Rock Wall", 
          new AutoDriveCmd(m_robot.getDriveTrain(), AutoDriveCmd.AutoMode.RockWall));
        m_autoChooser.addDefault("Disabled", 
          new AutoDriveCmd(m_robot.getDriveTrain(), AutoDriveCmd.AutoMode.Disabled));
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

