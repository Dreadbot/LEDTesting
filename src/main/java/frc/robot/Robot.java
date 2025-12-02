// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  private final int flame_count = 6;
  private final int led_count = 30;
  private final ArrayList<AddressableLEDBufferView> flames = new ArrayList<>();

 
  final AddressableLED m_led;
  final AddressableLEDBuffer m_ledBuffer;

  final LEDPattern flamePattern0;
  final LEDPattern flamePattern1;
  final LEDPattern flamePattern2;
  final LEDPattern flamePattern3;
  final LEDPattern flamePattern4;
  final LEDPattern flamePattern5;
  
  
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
  
    //PWM port 0
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(0);

    // Reuse buffer
      // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(flame_count*led_count);
    m_led.setLength(m_ledBuffer.getLength());

    for(int i=0; i < flame_count; i++){
      flames.add( m_ledBuffer.createView(i * led_count, i* led_count+led_count-1));
    }
  

    
    // create and LED patern that sets the entire strip to solid blue
    LEDPattern red = LEDPattern.solid(Color.kRed);
    LEDPattern redBreathe=red.breathe(Second.of(2));
    LEDPattern rainbow = LEDPattern.rainbow(255, 128);
    LEDPattern rainbowScroll = rainbow.scrollAtRelativeSpeed(Percent.per(Second).of(50));
    double percent_scroll = 25.0;
    LEDPattern gradient = LEDPattern.gradient(
        LEDPattern.GradientType.kContinuous, Color.kOrange, Color.kRed);
    LEDPattern scroll1 = gradient.scrollAtRelativeSpeed(Percent.per(Second).of(percent_scroll));
    LEDPattern scroll2 = gradient.scrollAtRelativeSpeed(Percent.per(Second).of(50));
    LEDPattern green = LEDPattern.solid(Color.kGreen);
    LEDPattern purple = LEDPattern.solid(Color.kPurple);
    LEDPattern redBreathe2 = red.breathe(Second.of(2));
    // apply the LED pattern to the buffer
    
   flamePattern0=purple;
   flamePattern1=scroll1;
   flamePattern2=redBreathe;
   flamePattern3=scroll2;
   flamePattern4=rainbowScroll;
   flamePattern5=redBreathe2;

    flamePattern0.applyTo(flames.get(0));
    flamePattern1.applyTo(flames.get(1));
    flamePattern2.applyTo(flames.get(2));
    flamePattern3.applyTo(flames.get(3));
    flamePattern4.applyTo(flames.get(4));
    flamePattern5.applyTo(flames.get(5));
   
    m_led.setData(m_ledBuffer);
    m_led.start();
    



    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    flamePattern0.applyTo(flames.get(0));
    flamePattern1.applyTo(flames.get(1));
    flamePattern2.applyTo(flames.get(2));
    flamePattern3.applyTo(flames.get(3));
    flamePattern4.applyTo(flames.get(4));
    flamePattern5.applyTo(flames.get(5));

    m_led.setData(m_ledBuffer);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
