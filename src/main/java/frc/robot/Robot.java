// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

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

  private final int flame_count;
  private final int led_count = 10;
  private final AddressableLEDBufferView[] flames;

  final AddressableLED m_led;
  final AddressableLEDBuffer m_ledBuffer;
  final LEDPattern current_pattern;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    flame_count = 0;
    //flames
    //PWM port 0
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(0);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(200);
    m_led.setLength(m_ledBuffer.getLength());

    // Create the view for the leaf sections 
    AddressableLEDBufferView m_leaf1 = m_ledBuffer.createView(0, 24);
    AddressableLEDBufferView m_leaf2 = m_ledBuffer.createView(25, 199);
  //AddressableLEDBufferView m_leaf3 = m_ledBuffer.createView(70, 94);
  //AddressableLEDBufferView m_leaf4 = m_ledBuffer.createView(105, 129);
  //AddressableLEDBufferView m_leaf5 = m_ledBuffer.createView(140, 164);
  //AddressableLEDBufferView m_leaf6 = m_ledBuffer.createView(175, 199);

    // create and LED patern that sets the entire strip to solid blue
    LEDPattern maroon = LEDPattern.solid(Color.kMaroon);
    LEDPattern blue = LEDPattern.solid(Color.kBlue);
    double percent_scroll = 25.0;
    LEDPattern gradient = LEDPattern.gradient(
        LEDPattern.GradientType.kContinuous, Color.kOrange, Color.kRed);
    LEDPattern scroll = gradient.scrollAtRelativeSpeed(Percent.per(Second).of(percent_scroll));
    
    // apply the LED pattern to the buffer
    
    //ledColor.applyTo(m_leaf3);
    //blue.applyTo(m_leaf4);
    //ledColor.applyTo(m_leaf5);
    //blue.applyTo(m_leaf6);
    current_pattern = scroll;
    // Set the data
    //m_led.setData(m_leaf1);
    blue.applyTo(m_leaf1);
    current_pattern.applyTo(m_leaf2);
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
    //current_pattern.applyTo(m_ledBuffer);
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
