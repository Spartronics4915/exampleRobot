package org.usfirst.frc.team4915.robot;
import org.usfirst.frc.team4915.robot.commands.*;
import org.usfirst.frc.team4915.robot.subsystems.*;

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
    // private fields ------------------------------------------------------
    private Robot m_robot;
    
    private Joystick m_driveStick;

    private Joystick m_lifterStick;
    private JoystickButton m_lightSwitchButton;
    private JoystickButton m_lifterManualButton;
    private JoystickButton m_lifterFwdButton;
    private JoystickButton m_lifterRevRutton;
    private JoystickButton m_lifterCycleButton;
    private JoystickButton m_lifterCheckLimitSwitchButton;

    private SendableChooser m_autoChooser;
    
    // constants -----------------------------------------------------------
    // drivestick port and buttons
    private final int k_driveStickPort = 0;
    private final int k_lightSwitchBID = 5;
 
    // liftstick port and buttons
    private final int k_liftStickPort = 1;
    private final int k_liftManualBID = 4; // manual control
    private final int k_liftFwdBID = 5; // auto fwd to limit switch
    private final int k_liftRevBID = 6; // auto rev to limit switch
    private final int k_liftCycleBID = 7; // auto back and forth 'tii canceled
    private final int k_liftCheckLimitSwitchBID = 8;
    
    // --------------------------------------------------------------------
    public OI(Robot robot)
    {
        m_robot = robot;
        
        // per-subsystem OI
        initDriveOI();
        initLifterOI();
        initGlobalOI();
        
        // dashboard view
        initDashboard();
       
    }
    
    private void initDriveOI()
    {
        m_driveStick = new Joystick(k_driveStickPort);
        m_robot.getDriveTrain().setDriveStick(m_driveStick);
    }
    
    private void initLifterOI()
    {
        Lifter l = m_robot.getLifter();
        m_lifterStick = new Joystick(k_liftStickPort);
        m_robot.getLifter().setLifterStick(m_lifterStick);
        m_lifterManualButton = new JoystickButton(m_lifterStick, k_liftManualBID);
        m_lifterManualButton.whenPressed(new LifterManualCtlCmd(l));
        m_lifterFwdButton = new JoystickButton(m_lifterStick, k_liftFwdBID);
        m_lifterFwdButton.whenPressed(new LifterAutoCtlCmd(l, true, false));
        m_lifterRevRutton = new JoystickButton(m_lifterStick, k_liftRevBID);
        m_lifterRevRutton.whenPressed(new LifterAutoCtlCmd(l, false, false));
        m_lifterCycleButton = new JoystickButton(m_lifterStick, k_liftCycleBID);
        m_lifterCycleButton.whenPressed(new LifterAutoCtlCmd(l, true, true));
        m_lifterCheckLimitSwitchButton = new JoystickButton(m_lifterStick, k_liftCheckLimitSwitchBID);
        m_lifterCheckLimitSwitchButton.whileHeld(new LifterCheckLimitSwitchCmd(l));
    }
    
    private void initGlobalOI()
    {
        m_lightSwitchButton = new JoystickButton(m_driveStick, k_lightSwitchBID);
        m_lightSwitchButton.whenPressed(
                        new LightSwitchCmd(m_robot.getPhotonicCannon()));
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
            m_robot.logger.notice("Build " + buildStr);;
        }
        catch (IOException e) 
        {
            m_robot.logger.error("Build version failure");;
            m_robot.logger.exception(e, true /*no stack trace needed*/);
        }
    }

    private void initAutoOI()
    {
        m_autoChooser = new SendableChooser();
        m_autoChooser.addDefault("Rock Wall", 
          new DriveAutoCmd(m_robot.getDriveTrain(), DriveAutoCmd.AutoMode.RockWall));
        m_autoChooser.addDefault("Disabled", 
          new DriveAutoCmd(m_robot.getDriveTrain(), DriveAutoCmd.AutoMode.Disabled));
        SmartDashboard.putData("AutoMode", m_autoChooser);
    }
    
    public Command getAutoCmd()
    {
        return (Command) m_autoChooser.getSelected();
    }
    
}

